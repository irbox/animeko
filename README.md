<div align="center">

![Animeko](https://socialify.git.ci/open-ani/animeko/image?description=1&descriptionEditable=%E9%9B%86%E6%89%BE%E7%95%AA%E3%80%81%E8%BF%BD%E7%95%AA%E3%80%81%E7%9C%8B%E7%95%AA%E7%9A%84%E4%B8%80%E7%AB%99%E5%BC%8F%E5%BC%B9%E5%B9%95%E8%BF%BD%E7%95%AA%E5%B9%B3%E5%8F%B0&font=Jost&logo=https%3A%2F%2Fraw.githubusercontent.com%2Fopen-ani%2Fanimeko%2Frefs%2Fheads%2Fmain%2F.github%2Fassets%2Flogo.png&name=1&owner=1&pattern=Plus&theme=Light)

| Downloads | Official version↓ | Beta↓ | Discussion Group |
|------------------------------------------------- -------------------------------------------------- -------------------------------------------------- -------------------------------------------------- -------------|---------------------------------------- -------------------------------------------------- -------------------------------------------------- ----------------------------------------|---------- -------------------------------------------------- -------------------------------------------------- -------------------------------------------------- ------------------------|------------------------- -------------------------------------------------- -------------------------------------------------- -------------------------------------------------- -------------------------------------------------- -------------------------------------------------- -------------------------------------------------- -------------------------------------------------- --------------------------|
| [![GitHub downloads](https://img.shields.io/github/downloads/open-ani/ani/total?label=Downloads&labelColor=27303D&color=0D1117&logo=github&logoColor=FFFFFF&style=flat)](https://github.com/open-ani/ani/releases) | [![Stable](https://img.shields.io/github/release/open-ani/ani.svg?maxAge=3600&label=Stable&labelColor=06599d&color=043b69)](https://github.com/open-ani/ani/releases/latest) | [![Beta](https://img.shields.io/github/v/release/open-ani/ani.svg?maxAge=3600&label=Beta&labelColor=2c2c47&color=1c1c39&include_prereleases)](https://github.com/open-ani/ani/releases) | [![Group](https://img.shields.io/badge/Telegram-2CA5E0?style=flat-squeare&logo=telegram&logoColor=white)](https://t.me/openani) [![QQ](https://img.shields.io/badge/927170241-EB1923?logo=tencent-qq&logoColor=white)](http://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=2EbZ0Qxe-fI_AHJLCMnSIOnqw-nfrFH5&authKey=L31zTMwfbMG0FhIgt8xNHGOFPHc531mSw2YzUVupHLRJ4L2f8xerAd%2ByNl4OigRK&noverify=0&group_code=927170241) |

</div>

[dmhy]: http://www.dmhy.org/

[Bangumi]: http://bangumi.tv

[ddplay]: https://www.dandanplay.com/

[Compose Multiplatform]: https://www.jetbrains.com/compose-multiplatform/

[acg.rip]: https://acg.rip

[Mikan]: https://mikanani.me/

[Ikaros]: https://ikaros.run/

[Kotlin Multiplatform]: https://kotlinlang.org/docs/multiplatform.html

[ExoPlayer]: https://developer.android.com/media/media3/exoplayer

[VLC]: https://www.videolan.org/vlc/

[libtorrent]: https://libtorrent.org/

Animeko supports cloud-synced viewing history ([Bangumi][Bangumi]), multiple video data sources, caching, bullet comments, and more features to provide anime-watching experience that is as simple and comfortable as possible.

## Technical Overview

The following points can give you a general understanding of the technology. If you are not interested, you can directly look at the [screenshots](#功能快照).

- Kotlin Multiplatform framework, supporting Windows, macOS, Android and iOS (planned)
- Based on Kotlin multi-platform architecture, use the new generation of responsive UI framework [Compose Multiplatform][Compose Multiplatform] to build UI
- Built-in BitTorrent engine based on [libtorrent][libtorrent] specially built for Animeko, optimizing the experience of downloading and playing at the same time
- High-performance barrage engine, public welfare barrage server + network barrage source
- Video player compatible with multiple platforms, the underlying platform for Android is [ExoPlayer][ExoPlayer], and the underlying platform for PC is [VLC][VLC]
- Multi-type data source adaptation, built-in [Anime Garden][dmhy], [Mikan], with a powerful custom data source editor

### Join the development

You are welcome to submit PR to participate in the development, and you are also welcome to join the open-ani organization.
For more technical details about the project, please refer to [CONTRIBUTING](CONTRIBUTING.md).

## download

Animeko supports Android and desktop (macOS, Windows).

- Stable version: updated every two weeks, with stable functions  
  [Download the stable version](https://github.com/Him188/ani/releases/latest)

It is usually recommended to use the stable version. If you are willing to participate in testing and have a certain ability to deal with bugs, you are also welcome to use the test version to experience new features faster.
The specific version types can be found below.

- Test version: Updated every two days to experience the latest features  
  [Download the test version](https://github.com/Him188/ani/releases)

<details>
<summary> <b>Click to view specific version type</b> </summary>

Animeko uses semantic versioning, which is in the format of `4.xy`. There are several types of versions:

- Stable version:
    - **New feature release**: When `x` is updated, new features will be released. Usually once every 2 weeks.
    - **Bug fixes**: When `y` is updated, there will only be important bug fixes for the previous version. These bug fix releases are interspersed between new feature updates.
      The time is not fixed.
- Between stable release cycles, test versions are released:
    - **Alpha**: All major new features will be released to the `alpha` testing channel first, and "daily builds" can be used in the client
      Receive updates. These new features are very unstable, suitable for enthusiastic pioneer testers!
    - **Beta test version**: After the function has been tested and major problems have been fixed, it will enter the `beta` testing channel.
      The client is named "Beta". This version is still unstable and is a balance between new features and stability.

</details>

## Functional Screenshots

### Management

- Multi-terminal synchronization [Bangumi] [Bangumi] Collection, record viewing progress

<img width="270" src=".readme/images/collection/collection-dark.png" alt="collection-dark"/> <img width="270" src=".readme/images/subject/subject -dark.png" alt="subject-dark"/>

<img width="600" src=".readme/images/collection/exploration-desktop.png" alt="exploration-desktop"/>

### Aggregation of multiple data sources

There is always a source that has the anime you want to watch and the subtitle group you like.

- Automatically integrate multiple BT data sources and online data sources, taking into account both resource quality and speed
- Intelligent selection algorithm to avoid the trouble of finding resources

<img width="270" src=".readme/images/episode/episode-player-loading.jpg" alt="episode-player-loading"/> <img width="270" src=".readme/images /episode/episode-stats.jpg" alt="episode-stats"/> <img width="270" src=".readme/images/episode/episode-media.jpg" alt="episode-media"/>

### Video Barrage

- Multiple bullet message data sources
- Efficient bullet screen engine, custom style
- Send bullet comments to Animeko public bullet comment server

<img width="600" src=".readme/images/episode/player-controller.png" alt="player-controller"/>

<img width="600" src=".readme/images/episode/player-danmaku.png" alt="player-danmaku"/>

<img width="600" src=".readme/images/episode/player-settings.png" alt="player-settings"/>

<img width="600" src=".readme/images/episode/episode-play-desktop.png" alt="episode-play-desktop"/>

<img width="600" src=".readme/images/episode/player-gesture.jpeg" alt="episode-gesture"/>

### Highly customizable

- Set the global priority subtitle group, subtitle language and other settings
- Modify the filter while watching, it will be automatically remembered and applied to the next playback and automatically cached

<img width="270" src=".readme/images/settings/settings.png" alt="settings"/> <img width="270" src=".readme/images/settings/settings-media.png " alt="settings-media"/>  

### cache

- Support offline playback

<img width="270" src=".readme/images/settings/cache.png" alt="cache"/> <img width="270" src=".readme/images/settings/global-caches.png " alt="global-caches"/>

### Completely free, ad-free and open source

- Use the reliable [Bangumi][Bangumi] to record the data of following anime, so you don't have to worry about the website running away and losing the data
- Video playback uses P2P resources, no server maintenance costs, ~Even if I run away, Animeko can still use it~
- Open source, public automatic build, no risk of data leakage
- You can PR to add your favorite features

## FAQ

### What are the sources?

All video data comes from the Internet, and Animeko itself does not store any video data.
Animeko supports two data source types: BT and online. BT source is the public BitTorrent P2P network.
Each in BT
Anyone on the Internet can share their own resources for others to download. Online sources are content shared by other video resource websites. Animeko itself does not provide any video resources.

In the spirit of mutual assistance, Animeko will automatically seed (share data) when using a BT source.
The BT fingerprint is `-aniLT3000-`, where `3000` is the version number; the UA is something like `ani_libtorrent/3.0.0`.

### What is the source of the barrage?

Animeko has its own public welfare bullet comment server. The bullet comments sent in the Animeko app will be sent to the bullet comment server. Each bullet comment will be in Bangumi
Username binding to prevent abuse (and consider adding reporting and blocking functions in the future).

Animeko will also obtain related barrages from [Dandanplay][ddplay], and Dandanplay will also obtain barrages from other barrage platforms such as Bilibili, Hong Kong, Macau and Taiwan, and Bahamut.
Each episode of an anime can have anywhere from dozens to thousands of comments.
