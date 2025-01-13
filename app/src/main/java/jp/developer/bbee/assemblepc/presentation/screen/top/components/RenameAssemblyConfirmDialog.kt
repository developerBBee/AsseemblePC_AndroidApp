package jp.developer.bbee.assemblepc.presentation.screen.top.components

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

@Composable
fun RenameAssemblyConfirmDialog(
    selectedName: String,
    newName: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {

    AlertDialog(
        shape = RoundedCornerShape(10.dp),
        onDismissRequest = onDismiss,
        title = { Text(text = "構成名の変更確認") },
        text = {
            Column {
                Text(text = selectedName, fontWeight = FontWeight.ExtraBold)
                Text(text = "を")
                Text(text = newName, fontWeight = FontWeight.ExtraBold)
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
                    onClick = onDismiss
                ) {
                    Text(text = "キャンセル")
                }

                Button(onClick = onConfirm) {
                    Text(text = "確定")
                }
            }
        }
    )
}