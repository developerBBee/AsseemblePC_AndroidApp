package jp.developer.bbee.assemblepc.common

sealed class DatabaseResponse<T>(
    val data: T? = null,
    val error: String? = null
){
    class Success<T>(data: T) : DatabaseResponse<T>(data = data)
    class Failure<T>(error: String) : DatabaseResponse<T>(error = error)
    class Loading<T> : DatabaseResponse<T>()
}
