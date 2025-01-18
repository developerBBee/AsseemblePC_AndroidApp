package jp.developer.bbee.assemblepc.presentation.screen.device.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SortMenu(
    currentSortType: SortType,
    onSortChanged: (SortType) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        IconButton(
            onClick = { isExpanded = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "ソートメニューボタン",
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 5.dp, end = 5.dp, top = 8.dp)
            )
        }

        Row (Modifier.padding(end = 16.dp)) {
            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
            ) {
                for(sortType: SortType in SortType.entries) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        DropdownMenuItem(
                            onClick = {
                                isExpanded = false
                                onSortChanged(sortType)
                            }
                        ) {
                            if (currentSortType == sortType) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "選択アイコン",
                                    modifier = Modifier
                                        .width(24.dp)
                                        .padding(end = 2.dp)
                                )
                            } else {
                                Spacer(modifier = Modifier
                                    .width(24.dp)
                                    .padding(end = 2.dp))
                            }
                            Text(text = sortType.sortName)
                        }
                    }
                }
            }
        }
    }
}
