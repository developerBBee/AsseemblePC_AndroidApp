package jp.developer.bbee.assemblepc.common

sealed class DatabaseResponse<T>(
    val error: String? = null
){
    class Failure<T>(error: String) : DatabaseResponse<T>(error = error)
    class Access<T> : DatabaseResponse<T>()
    class SuccessDml<T> : DatabaseResponse<T>()
}
