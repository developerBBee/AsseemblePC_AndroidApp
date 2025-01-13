package jp.developer.bbee.assemblepc.domain.model

import kotlinx.serialization.Serializable

/**
 * 構成情報
 */
@Serializable
data class Composition(
    val assemblyId: Int,
    val assemblyName: String,
    val items: List<Assembly> = emptyList()
)
