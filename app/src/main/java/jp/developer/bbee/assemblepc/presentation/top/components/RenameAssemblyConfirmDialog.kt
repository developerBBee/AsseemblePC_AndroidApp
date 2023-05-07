package jp.developer.bbee.assemblepc.presentation.top.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import jp.developer.bbee.assemblepc.presentation.top.TopViewModel

@Composable
fun RenameAssemblyConfirmDialog(
    topViewModel: TopViewModel
) {
    val selectedId = topViewModel.selectedAssemblyId ?: return
    val selectedList = topViewModel.allAssemblyMap[selectedId] ?: return
    val selectedName = selectedList[0].assemblyName
    val renameStr = topViewModel.renameStr

    AlertDialog(
        shape = RoundedCornerShape(10.dp),
        onDismissRequest = {
            topViewModel.closeEditDialog()
        },
        title = { Text(text = "構成名の変更確認") },
        text = {
            Column {
                Text(text = selectedName, fontWeight = FontWeight.ExtraBold)
                Text(text = "を")
                Text(text = renameStr, fontWeight = FontWeight.ExtraBold)
                Text(text = "に変更します")
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
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    modifier = Modifier.padding(end = 10.dp),
                    onClick = {
                        topViewModel.closeEditDialog()
                    }
                ) {
                    Text(text = "キャンセル")
                }
                Button(
                    onClick = {
                        topViewModel.renameAssembly()
                    }
                ) {
                    Text(text = "確定")
                }
            }
        }
    )
}