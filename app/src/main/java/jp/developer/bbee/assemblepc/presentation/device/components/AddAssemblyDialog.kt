package jp.developer.bbee.assemblepc.presentation.device.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import jp.developer.bbee.assemblepc.presentation.device.AssemblyViewModel

@Composable
fun AddAssemblyDialog(
    assemblyViewModel: AssemblyViewModel = hiltViewModel()
) {
    val device = assemblyViewModel.selectedDevice
    AlertDialog(
        onDismissRequest = { assemblyViewModel.isShowDialog = false },
        title = {
            Text(
                text = "構成に追加しますか？",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
            )
        },
        text = {
            Row {
                AsyncImage(
                    model = device?.imgurl,
                    modifier = Modifier
                        .height(80.dp)
                        .width(80.dp),
                    contentDescription = "製品画像"
                )
                Column {
                    Text(text = device?.name ?: "製品不明")
                    Text(
                        text = if(device == null) "製品不明"
                        else if(device.price == 0) "価格情報なし" else "¥ ${device.price}"
                    )
                }
            }
        },
        buttons = {
            Row (modifier = Modifier.padding(10.dp)) {
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = { assemblyViewModel.isShowDialog = false }) {
                    Text(text = "キャンセル")
                }
                Button(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    onClick = {
                        assemblyViewModel.isShowDialog = false
                        assemblyViewModel.addAssembly()
                    }
                ) {
                    Text(text = "追加")
                }
            }
        }
    )
}