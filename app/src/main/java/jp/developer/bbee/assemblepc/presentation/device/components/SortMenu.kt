package jp.developer.bbee.assemblepc.presentation.device.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.developer.bbee.assemblepc.presentation.device.components.SortType.*

@Preview
@Composable
fun SortMenu() {
    var isExpanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(POPULARITY) }

    val sortTypes = listOf(
        POPULARITY,
        NEW_ARRIVAL,
        PRICE_DESC,
        PRICE_ASC,
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        IconButton(
            onClick = { isExpanded = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "ソートメニューボタン",
                modifier = Modifier.fillMaxHeight().padding(start = 5.dp, end = 5.dp, top = 8.dp)
            )
        }

        Row (Modifier.padding(end = 16.dp)) {
            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
            ) {
                sortTypes.forEach { sortType ->
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        DropdownMenuItem(
                            onClick = {
                                isExpanded = false
                                selected = sortType
                            }
                        ) {
                            if (selected == sortType) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "選択アイコン",
                                    modifier = Modifier.width(24.dp).padding(end = 2.dp)
                                )
                            } else {
                                Spacer(modifier = Modifier.width(24.dp).padding(end = 2.dp))
                            }
                            Text(text = sortType.sortName)
                        }
                    }
                }
            }
        }
    }
}