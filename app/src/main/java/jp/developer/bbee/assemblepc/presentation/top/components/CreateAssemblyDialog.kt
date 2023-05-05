package jp.developer.bbee.assemblepc.presentation.top.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.SelectionScreen

@Composable
fun CreateAssemblyDialog(
    navController: NavController,
    showDialogState: MutableState<Boolean>,
) {
    var assemblyName by remember { mutableStateOf("") }
    AlertDialog(
        shape = RoundedCornerShape(10.dp),
        onDismissRequest = { showDialogState.value = false },
        title = {
            Text(
                text = "構成を新規作成しますか？",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
            )
        },
        text = {
            Column {
                Text(
                    text = "構成の名称を記入",
                    fontWeight = FontWeight.Bold
                )
                TextField(
                    value = assemblyName,
                    onValueChange = { if (it.length <= 20) assemblyName = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "構成名称") },
                    maxLines = 1,
                    singleLine = true,
                )

            }
        },
        buttons = {
            Row (modifier = Modifier.padding(10.dp)) {
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = { showDialogState.value = false }) {
                    Text(text = "キャンセル")
                }
                Button(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    onClick = {
                        showDialogState.value = false
                        val name = if(assemblyName == "") "構成名なし" else assemblyName
                        navController.navigate(SelectionScreen.route + "/0" + "/$name" + "/pccase")
                    }
                ) {
                    Text(text = "新規作成")
                }
            }
        }
    )
}