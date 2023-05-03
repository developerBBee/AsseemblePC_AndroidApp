package jp.developer.bbee.assemblepc.presentation.device.components

enum class SortType(val sortName: String, val sortColumn: String, val orderBy: String) {
    POPULARITY("人気順", "rank", "desc"),
    NEW_ARRIVAL("新着順", "releasedate", "asc"),
    PRICE_ASC("値段が安い順", "price", "asc"),
    PRICE_DESC("値段が高い順", "price", "desc")
}
