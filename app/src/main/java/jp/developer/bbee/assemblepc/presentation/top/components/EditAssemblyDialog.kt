package jp.developer.bbee.assemblepc.presentation.top.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.AssemblyScreen
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.SelectionScreen
import jp.developer.bbee.assemblepc.presentation.top.TopViewModel

@Composable
fun EditAssemblyDialog(
    navController: NavController,
    topViewModel: TopViewModel,
) {
    val selectedId = topViewModel.selectedAssemblyId ?: return
    val selectedList = topViewModel.allAssemblyMap[selectedId] ?: return
    val selectedName = selectedList[0].assemblyName
    val name = if (selectedName == "") "構成名なし" else selectedName
    val navPath = "/${selectedId}/${name}/pccase"

    var assemblyName by remember { mutableStateOf(selectedName) }
    var changeNameButtonEnable by remember { mutableStateOf(false) }
    AlertDialog(
        shape = RoundedCornerShape(10.dp),
        onDismissRequest = { topViewModel.closeEditDialog() },
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
                    value = assemblyName,
                    onValueChange = {
                        if (it.length <= 20) {
                            assemblyName = it
                            changeNameButtonEnable = true
                        } },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "構成名称") },
                    maxLines = 1,
                    singleLine = true,
                )
                Button(
                    modifier = Modifier.align(Alignment.End),
                    enabled = changeNameButtonEnable,
                    onClick = {
                        changeNameButtonEnable = false
                        /*TODO*/
                    }
                ) {
                    Text(text = "確定")
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
                    Button(
                        onClick = {
                            topViewModel.deleteConfirm = true
                        }
                    ) {
                        Text(text = "構成を削除")
                    }
                    Button(
                        onClick = { topViewModel.closeEditDialog() }
                    ) {
                        Text(text = "キャンセル")
                    }
                }
                // 右側ボタン（上：パーツを追加　下：構成確認）
                Column {
                    Button(
                        modifier = Modifier.align(Alignment.End),
                        onClick = {
                            topViewModel.closeEditDialog()
                            navController.navigate(SelectionScreen.route + navPath)
                        }
                    ) {
                        Text(text = "パーツを追加")
                    }
                    Button(
                        modifier = Modifier.align(Alignment.End),
                        onClick = {
                            topViewModel.closeEditDialog()
                            navController.navigate(AssemblyScreen.route + navPath)
                        }
                    ) {
                        Text(text = "構成確認")
                    }
                }
            }
        }
    )
}