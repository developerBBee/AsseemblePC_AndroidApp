package jp.developer.bbee.assemblepc.presentation.screen.top.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AssemblyReviewDialog(
    reviewText: String?,
    reviewRequestEnabled: Boolean,
    reviewing: Boolean,
    onDismiss: () -> Unit,
    onStartReview: () -> Unit,
) {
    AlertDialog(
        shape = RoundedCornerShape(10.dp),
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text(
                    text = "AIによる構成レビュー",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (reviewing) {
                    CircularProgressIndicator()
                } else {
                    reviewText?.let { text ->
                        Text(
                            text = text,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                        )
                    }
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
                Spacer(modifier = Modifier.weight(1f))

                Button(
                    modifier = Modifier.padding(end = 10.dp),
                    onClick = onDismiss
                ) {
                    Text(text = "閉じる")
                }

                if (reviewRequestEnabled) {
                    Button(onClick = onStartReview, enabled = !reviewing) {
                        Text(text = "AIレビュー")
                    }
                }
            }
        }
    )
}