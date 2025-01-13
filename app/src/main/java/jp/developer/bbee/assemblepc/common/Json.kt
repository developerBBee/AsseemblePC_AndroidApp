package jp.developer.bbee.assemblepc.common

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
val defaultJson = Json {
    ignoreUnknownKeys = true // デシリアライズ時に未知のプロパティを無視する
    encodeDefaults = true // シリアル化の際にデフォルト値を含める
}
