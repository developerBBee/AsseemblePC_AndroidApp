package jp.developer.bbee.assemblepc.presentation.selection.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonsRow(
    leftContentText: String,
    rightContentText: String? = null,
    onClickLeftButton: () -> Unit,
    onClickRightButton: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        val leftContentTextSize = 26 - leftContentText.replace("\n", "").length
        Button(
            modifier = Modifier
                .width(160.dp).height(120.dp)
                .clip(shape = RoundedCornerShape(20.dp)),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF1E90FF),
                contentColor = Color(0xFFFFFFF0)
            ),
            onClick = onClickLeftButton
        ) {
            Text(
                text = leftContentText,
                fontSize = leftContentTextSize.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
        if (rightContentText != null) {
            val rightContentTextSize = 26 - rightContentText.replace("\n", "").length
            Button(
                modifier = Modifier.width(160.dp).height(120.dp)
                    .clip(shape = RoundedCornerShape(20.dp)),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF1E90FF),
                    contentColor = Color(0xFFFFFFF0)
                ),
                onClick = onClickRightButton
            ) {
                Text(
                    text = rightContentText,
                    fontSize = rightContentTextSize.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            Spacer(Modifier.width(160.dp).height(120.dp))
        }
    }
}