package jp.developer.bbee.assemblepc.presentation.device

data class AssemblyState(
    val isAccessing: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)
