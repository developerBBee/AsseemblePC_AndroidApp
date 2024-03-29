package jp.developer.bbee.assemblepc.presentation.top.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.SelectionScreen
import jp.developer.bbee.assemblepc.presentation.top.TopViewModel

@Composable
fun CreateAssemblyDialog(
    navController: NavController,
    topViewModel: TopViewModel,
) {
    AlertDialog(
        shape = RoundedCornerShape(10.dp),
        onDismissRequest = { topViewModel.showCreateDialog = false },
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
                    value = topViewModel.createAssemblyName,
                    onValueChange = { if (it.length <= 20) topViewModel.createAssemblyName = it },
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
                Button(onClick = { topViewModel.showCreateDialog = false }) {
                    Text(text = "キャンセル")
                }
                Button(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    onClick = {
                        topViewModel.showCreateDialog = false
                        val name = if(topViewModel.createAssemblyName == "") "構成名なし"
                            else topViewModel.createAssemblyName.replace("/", "燬／")
                        navController.navigate(SelectionScreen.route + "/0" + "/$name" + "/pccase")
                    }
                ) {
                    Text(text = "新規作成")
                }
            }
        }
    )
}