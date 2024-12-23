/*
 * Copyright (C) 2024 OpenAni and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license, which can be found at the following link.
 *
 * https://github.com/open-ani/ani/blob/main/LICENSE
 */

package me.him188.ani.app.data.persistent.database.dao

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Transaction
import androidx.room.Upsert
import me.him188.ani.datasources.api.EpisodeSort

@Entity(
    tableName = "web_search_episode",
    indices = [
        Index(value = ["parentId"]),
        Index(value = ["channel", "name", "parentId"], unique = true),
    ],
    foreignKeys = [
        ForeignKey(
            entity = WebSearchSubjectInfoEntity::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class WebSearchEpisodeInfoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val channel: String?,
    val name: String,
    val episodeSortOrEp: EpisodeSort?,
    val playUrl: String,
    val parentId: Long,
)

@Dao
interface WebSearchEpisodeInfoDao {
    @Upsert
    suspend fun upsert(item: WebSearchEpisodeInfoEntity)

    @Upsert
    @Transaction
    suspend fun upsert(item: List<WebSearchEpisodeInfoEntity>)
}