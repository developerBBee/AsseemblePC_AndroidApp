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
import jp.developer.bbee.assemblepc.presentation.device.DeviceViewModel

@Composable
fun AddAssemblyDialog(
    viewModel: DeviceViewModel = hiltViewModel()
//    title: String,
//    imgurl: String,
//    description: String,
//    onConfirm: () -> Unit,
//    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { viewModel.isShowDialog = false },
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
                    model = "https://img1.kakaku.k-img.com/images/productimage/l/J0000036429.jpg",
                    modifier = Modifier
                        .height(80.dp)
                        .width(80.dp),
                    contentDescription = "仮置きdescription"
                )
                Column {
                    Text(text = "製品名")
                    Text(text = "¥ 10,000")
                }
            }
        },
        buttons = {
            Row (modifier = Modifier.padding(10.dp)) {
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = { viewModel.isShowDialog = false }) {
                    Text(text = "キャンセル")
                }
                Button(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    onClick = {
                        /* TODO */
                        viewModel.isShowDialog = false
                    }
                ) {
                    Text(text = "追加")
                }
            }
        }
    )
}