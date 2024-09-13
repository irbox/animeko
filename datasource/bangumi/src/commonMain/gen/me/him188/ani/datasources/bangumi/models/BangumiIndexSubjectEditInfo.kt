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
    "UnusedImport"
)

package me.him188.ani.datasources.bangumi.models


import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

/**
 * 修改目录中条目的信息
 *
 * @param sort 排序条件，越小越靠前
 * @param comment 
 */
@Serializable

data class BangumiIndexSubjectEditInfo(

    /* 排序条件，越小越靠前 */
    @SerialName(value = "sort") val sort: kotlin.Int? = null,

    @SerialName(value = "comment") val comment: kotlin.String? = null

)
