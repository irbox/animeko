/*
 * Copyright (C) 2024 OpenAni and contributors.
 *
 * Use of this source code is governed by the GNU AFFERO GENERAL PUBLIC LICENSE version 3 license, which can be found at the following link.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.android

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.io.files.Path
import me.him188.ani.android.activity.MainActivity
import me.him188.ani.android.navigation.AndroidBrowserNavigator
import me.him188.ani.app.data.models.preference.AnitorrentConfig
import me.him188.ani.app.data.models.preference.ProxySettings
import me.him188.ani.app.data.models.preference.TorrentPeerConfig
import me.him188.ani.app.data.repository.user.SettingsRepository
import me.him188.ani.app.domain.media.fetch.MediaSourceManager
import me.him188.ani.app.domain.media.resolver.AndroidWebVideoSourceResolver
import me.him188.ani.app.domain.media.resolver.HttpStreamingVideoSourceResolver
import me.him188.ani.app.domain.media.resolver.LocalFileVideoSourceResolver
import me.him188.ani.app.domain.media.resolver.TorrentVideoSourceResolver
import me.him188.ani.app.domain.media.resolver.VideoSourceResolver
import me.him188.ani.app.domain.torrent.DefaultTorrentManager
import me.him188.ani.app.domain.torrent.LocalAnitorrentEngineFactory
import me.him188.ani.app.domain.torrent.TorrentEngine
import me.him188.ani.app.domain.torrent.TorrentEngineFactory
import me.him188.ani.app.domain.torrent.TorrentManager
import me.him188.ani.app.domain.torrent.client.RemoteAnitorrentEngine
import me.him188.ani.app.domain.torrent.service.AniTorrentService
import me.him188.ani.app.domain.torrent.service.TorrentServiceConnection
import me.him188.ani.app.navigation.BrowserNavigator
import me.him188.ani.app.platform.AndroidPermissionManager
import me.him188.ani.app.platform.AppTerminator
import me.him188.ani.app.platform.BaseComponentActivity
import me.him188.ani.app.platform.ContextMP
import me.him188.ani.app.platform.PermissionManager
import me.him188.ani.app.platform.findActivity
import me.him188.ani.app.platform.notification.AndroidNotifManager
import me.him188.ani.app.platform.notification.NotifManager
import me.him188.ani.app.tools.update.AndroidUpdateInstaller
import me.him188.ani.app.tools.update.UpdateInstaller
import me.him188.ani.app.videoplayer.ExoPlayerStateFactory
import me.him188.ani.app.videoplayer.ui.state.PlayerStateFactory
import me.him188.ani.utils.io.SystemPath
import me.him188.ani.utils.io.deleteRecursively
import me.him188.ani.utils.io.exists
import me.him188.ani.utils.io.inSystem
import me.him188.ani.utils.io.isDirectory
import me.him188.ani.utils.io.list
import me.him188.ani.utils.io.resolve
import me.him188.ani.utils.logging.logger
import me.him188.ani.utils.logging.warn
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.io.File
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext
import kotlin.system.exitProcess

fun getAndroidModules(
    defaultTorrentCacheDir: File,
    torrentServiceConnection: TorrentServiceConnection,
    coroutineScope: CoroutineScope,
) = module {
    single<PermissionManager> {
        AndroidPermissionManager()
    }
    single<NotifManager> {
        AndroidNotifManager(
            NotificationManagerCompat.from(androidContext()),
            getContext = { androidContext() },
            activityIntent = {
                Intent(androidContext(), MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                }
//                androidContext().packageManager.getLaunchIntentForPackage(androidContext().packageName)
//                    ?: Intent(Intent.ACTION_MAIN).apply {
//                        setPackage(androidContext().packageName)
//                    }
            },
            coroutineScope.coroutineContext,
        ).apply { createChannels() }
    }
    single<BrowserNavigator> { AndroidBrowserNavigator() }

    single<TorrentServiceConnection> { torrentServiceConnection }
    
    single<TorrentManager> {
        val context = androidContext()
        val defaultTorrentCachePath = defaultTorrentCacheDir.absolutePath
        val cacheDir = runBlocking {
            val settings = get<SettingsRepository>().mediaCacheSettings
            val dir = settings.flow.first().saveDir

            // Set to the private directory inside the application when launching for the first time
            if (dir == null) {
                settings.update { copy(saveDir = defaultTorrentCachePath) }
                return@runBlocking defaultTorrentCachePath
            }

            if (dir.startsWith(context.filesDir.absolutePath)) {
                // The private directory is saved in the settings, return directly
                return@runBlocking dir
            }

//            context.contentResolver.persistedUriPermissions.forEach { p ->
//                val storage = DocumentsContractApi19.parseUriToStorage(context, p.uri)
//
//                if (storage != null && dir.startsWith(storage)) {
//                    return@runBlocking if (p.isReadPermission && p.isWritePermission) {
//                        // You need to verify the directory permissions again
//                        try {
//                            withContext(Dispatchers.IO) {
//                                File(storage).resolve("pieces/.nomedia")
//                                    .apply { parentFile.mkdirs() }
//                                    .apply { createNewFile() }
//                                    .writeText(" ")
//                            }
//                            dir
//                        } catch (ex: IOException) {
//                            // Actually no permission, release uri
//                            logger.warn(ex) { "failed to write to .nomedia" }
//                            context.contentResolver.releasePersistableUriPermission(
//                                p.uri, Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION,
//                            )
//                            resetToDefault()
//                        }
//                    } else {
//                        // The external shared directory saved in the settings does not have full read and write permissions, so switch directly back to the default internal private directory,
//                        // Avoid App crashes caused by insufficient read and write permissions
//                        resetToDefault()
//                    }
//                }
//            }

            // Checking external private directories
            if (context.getExternalFilesDir(null) == null) {
                // The external private directory is unavailable, so switch back to the default private directory to avoid App crashes caused by insufficient read and write permissions.
                settings.update { copy(saveDir = defaultTorrentCachePath) }
                Toast.makeText(context, "BT storage location is unavailable, switched back to default storage location", Toast.LENGTH_LONG).show()
                return@runBlocking defaultTorrentCachePath
            }

            // External private directory available
            dir
        }
        
        val oldCacheDir = Path(cacheDir).resolve("api").inSystem
        if (oldCacheDir.exists() && oldCacheDir.isDirectory()) {
            val piecesDir = oldCacheDir.resolve("pieces")
            if (piecesDir.exists() && piecesDir.isDirectory() && piecesDir.list().isNotEmpty()) {
                Toast.makeText(context, "旧 BT 引擎的缓存已不被支持，请重新缓存", Toast.LENGTH_LONG).show()
            }
            thread(name = "DeleteOldCaches") { 
                try { 
                    oldCacheDir.deleteRecursively() 
                } catch (ex: Exception) { 
                    logger<TorrentManager>().warn(ex) { "Failed to delete old caches in $oldCacheDir" }
                }
            }
        }

        DefaultTorrentManager.create(
            coroutineScope.coroutineContext,
            get(),
            get(),
            baseSaveDir = { Path(cacheDir).inSystem },
            if (AniApplication.FEATURE_USE_TORRENT_SERVICE) {
                object : TorrentEngineFactory {
                    override fun createTorrentEngine(
                        parentCoroutineContext: CoroutineContext,
                        config: Flow<AnitorrentConfig>,
                        proxySettings: Flow<ProxySettings>,
                        peerFilterSettings: Flow<TorrentPeerConfig>,
                        saveDir: SystemPath
                    ): TorrentEngine {
                        return RemoteAnitorrentEngine(
                            get(),
                            config,
                            proxySettings,
                            peerFilterSettings,
                            saveDir,
                            parentCoroutineContext,
                        )
                    }
                }
            } else {
                LocalAnitorrentEngineFactory
            },
        )
    }
    single<PlayerStateFactory> { ExoPlayerStateFactory() }


    factory<VideoSourceResolver> {
        VideoSourceResolver.from(
            get<TorrentManager>().engines
                .map { TorrentVideoSourceResolver(it) }
                .plus(LocalFileVideoSourceResolver())
                .plus(HttpStreamingVideoSourceResolver())
                .plus(AndroidWebVideoSourceResolver(get<MediaSourceManager>().webVideoMatcherLoader)),
        )
    }
    single<UpdateInstaller> { AndroidUpdateInstaller() }

    single<AppTerminator> {
        object : AppTerminator {
            override fun exitApp(context: ContextMP, status: Int): Nothing {
                runBlocking(Dispatchers.Main.immediate) {
                    (context.findActivity() as? BaseComponentActivity)?.finishAffinity()
                    context.startService(
                        Intent(context, AniTorrentService::class.java)
                            .apply { putExtra("stopService", true) },
                    )
                    exitProcess(status)
                }
            }
        }
    }
}
