package jp.developer.bbee.assemblepc.presentation.screen.device.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import jp.developer.bbee.assemblepc.domain.model.Device

@Composable
fun AddAssemblyDialog(
    device: Device,
    onDismiss: () -> Unit,
    onAddAssembly: (Device) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(10.dp),
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
                    model = device.imgurl,
                    modifier = Modifier
                        .height(80.dp)
                        .width(80.dp),
                    contentDescription = "製品画像"
                )

                Column {
                    Text(text = device.name)

                    Text(text = device.price.yenOrUnknown())
                }
            }
        },
        buttons = {
            Row (modifier = Modifier.padding(10.dp)) {
                Spacer(modifier = Modifier.weight(1f))

                Button(onClick = onDismiss) {
                    Text(text = "キャンセル")
                }

                Button(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    onClick = { onAddAssembly(device) }
                ) {
                    Text(text = "追加")
                }
            }
        }
    )
}