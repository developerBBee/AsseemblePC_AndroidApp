package jp.developer.bbee.assemblepc.presentation.screen.top.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditAssemblyDialog(
    selectedName: String,
    onDismiss: () -> Unit,
    onAddParts: () -> Unit,
    onShowComposition: () -> Unit,
    onRenameClick: (String) -> Unit,
    onDeleteClick: () -> Unit,
) {
    var newName: String by remember { mutableStateOf(selectedName) }

    val changeNameButtonEnable = (newName != selectedName) && newName.isNotEmpty()

    AlertDialog(
        shape = RoundedCornerShape(10.dp),
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text(
                    text = selectedName,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                )
                Text(
                    text = "を選択中",
                    fontSize = 16.sp,
                )
            }
        },
        text = {
            Column {
                Text(
                    text = "構成の名称(変更)",
                    fontWeight = FontWeight.Bold
                )
                TextField(
                    value = newName,
                    onValueChange = { if (it.length <= 20) newName = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "構成名称") },
                    maxLines = 1,
                    singleLine = true,
                )
                Button(
                    modifier = Modifier.align(Alignment.End),
                    enabled = changeNameButtonEnable,
                    onClick = { onRenameClick(newName) }
                ) {
                    Text(text = "更新")
                }
            }
        },
        buttons = {
            Row(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp, bottom = 5.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                // 左側ボタン（上：構成を削除　下：キャンセル）
                Column {
                    Button(onClick = onDeleteClick) {
                        Text(text = "構成を削除")
                    }

                    Button(onClick = onDismiss) {
                        Text(text = "キャンセル")
                    }
                }

                // 右側ボタン（上：パーツを追加　下：構成確認）
                Column {
                    Button(
                        modifier = Modifier.align(Alignment.End),
                        onClick = onAddParts
                    ) {
                        Text(text = "パーツを追加")
                    }

                    Button(
                        modifier = Modifier.align(Alignment.End),
                        onClick = onShowComposition
                    ) {
                        Text(text = "構成確認")
                    }
                }
            }
        }
    )
}