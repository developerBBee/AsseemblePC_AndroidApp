package jp.developer.bbee.assemblepc.presentation.device.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun SortMenu() {
    var isExpanded by remember { mutableStateOf(false) }

    val sortTypes = listOf(
        SortType.POPULARITY,
        SortType.NEW_ARRIVAL,
        SortType.PRICE_DESC,
        SortType.PRICE_ASC,
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        IconButton(onClick = { isExpanded = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "ソートメニューボタン"
            )
        }

        Row (Modifier.padding(end = 16.dp)) {
            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
            ) {
                sortTypes.forEach { sortType ->
                    DropdownMenuItem(
                        onClick = {
                            // TODO: ソート処理を呼ぶ
                        }
                    ) {
                        Text(text = sortType.sortName)
                    }
                }
            }
        }
    }
}