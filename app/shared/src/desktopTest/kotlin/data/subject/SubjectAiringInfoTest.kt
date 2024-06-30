package me.him188.ani.app.data.subject

import me.him188.ani.datasources.api.EpisodeSort
import kotlin.test.Test
import kotlin.test.assertEquals

class SubjectAiringInfoTest {
    private var idCounter = 0

    private fun ep(
        sort: Int,
        airDate: PackedDate = PackedDate.Invalid,
    ): EpisodeInfo = EpisodeInfo(id = ++idCounter, sort = EpisodeSort(sort), airDate = airDate)

    @Test
    fun `empty episode list is upcoming`() {
        val info = SubjectAiringInfo.computeFromEpisodeList(emptyList(), PackedDate.Invalid)
        assertEquals(SubjectAiringKind.UPCOMING, info.kind)
        assertEquals(0, info.episodeCount)
        assertEquals(PackedDate.Invalid, info.airDate)
        assertEquals(null, info.firstSort)
        assertEquals(null, info.latestSort)
        assertEquals(null, info.upcomingSort)
    }

    @Test
    fun `single episode upcoming`() {
        val eps = listOf(
            ep(3, PackedDate(8888, 1, 8 + 7 * 2)),
        )
        val info = SubjectAiringInfo.computeFromEpisodeList(eps, PackedDate.Invalid)
        assertEquals(SubjectAiringKind.UPCOMING, info.kind)
        assertEquals(1, info.episodeCount)
        assertEquals(PackedDate(8888, 1, 8 + 7 * 2), info.airDate)
        assertEquals(EpisodeSort(3), info.firstSort)
        assertEquals(null, info.latestSort)
        assertEquals(EpisodeSort(3), info.upcomingSort)
    }

    @Test
    fun `all episodes are completed`() {
        val eps = listOf(
            ep(1, PackedDate(2023, 1, 8)),
            ep(2, PackedDate(2023, 1, 8 + 7)),
            ep(3, PackedDate(2023, 1, 8 + 7 * 2)),
        )
        val info = SubjectAiringInfo.computeFromEpisodeList(
            eps,
            PackedDate.Invalid,
        )
        assertEquals(SubjectAiringKind.COMPLETED, info.kind)
        assertEquals(3, info.episodeCount)
        assertEquals(PackedDate(2023, 1, 8), info.airDate)
        assertEquals(EpisodeSort(1), info.firstSort)
        assertEquals(EpisodeSort(3), info.latestSort)
        assertEquals(null, info.upcomingSort)
    }

    @Test
    fun `some episodes completed, some upcoming`() {
        val eps = listOf(
            ep(1, PackedDate(2023, 1, 8)),
            ep(2, PackedDate(2023, 1, 8 + 7)),
            ep(3, PackedDate(8888, 1, 8 + 7 * 2)),
        )
        val info = SubjectAiringInfo.computeFromEpisodeList(
            eps,
            PackedDate.Invalid,
        )
        assertEquals(SubjectAiringKind.ON_AIR, info.kind)
        assertEquals(3, info.episodeCount)
        assertEquals(PackedDate(2023, 1, 8), info.airDate)
        assertEquals(EpisodeSort(1), info.firstSort)
        assertEquals(EpisodeSort(2), info.latestSort)
        assertEquals(EpisodeSort(3), info.upcomingSort)
    }
}