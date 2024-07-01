/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport",
)

package me.him188.ani.datasources.bangumi.models

import me.him188.ani.datasources.bangumi.models.BangumiImages
import me.him188.ani.datasources.bangumi.models.BangumiItem

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

/**
 * 同名字段意义同<a href=\"#model-Subject\">Subject</a>
 *
 * @param id
 * @param type
 * @param name
 * @param comment
 * @param addedAt
 * @param images
 * @param infobox
 * @param date
 */
@Serializable

data class BangumiIndexSubject(

    @SerialName(value = "id") @Required val id: kotlin.Int,

    @SerialName(value = "type") @Required val type: kotlin.Int,

    @SerialName(value = "name") @Required val name: kotlin.String,

    @SerialName(value = "comment") @Required val comment: kotlin.String,

    @SerialName(value = "added_at") @Required val addedAt: kotlinx.datetime.Instant,

    @SerialName(value = "images") val images: BangumiImages? = null,

    @SerialName(value = "infobox") val infobox: kotlin.collections.List<BangumiItem>? = null,

    @SerialName(value = "date") val date: kotlin.String? = null

)
