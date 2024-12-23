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

package me.him188.ani.datasources.bangumi.next.models

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 *
 * @param count
 * @param sim
 * @param subject
 */
@Serializable

data class BangumiNextSubjectRec(

    @SerialName(value = "count") @Required val count: kotlin.Int,

    @SerialName(value = "sim") @Required val sim: @Serializable(me.him188.ani.utils.serialization.BigNumAsDoubleStringSerializer::class) me.him188.ani.utils.serialization.BigNum,

    @SerialName(value = "subject") @Required val subject: BangumiNextSlimSubject

)
