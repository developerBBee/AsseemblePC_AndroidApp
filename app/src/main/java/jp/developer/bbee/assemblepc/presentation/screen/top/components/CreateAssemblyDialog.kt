package jp.developer.bbee.assemblepc.presentation.screen.top.components

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.developer.bbee.assemblepc.BuildConfig

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreateAssemblyDialog(
    onDismiss: () -> Unit,
    onCreationStart: (String) -> Unit = {},
) {
    var assemblyName: String by remember { mutableStateOf("") }

    AlertDialog(
        modifier = Modifier
            .semantics { testTagsAsResourceId = BuildConfig.DEBUG },
        shape = RoundedCornerShape(10.dp),
        onDismissRequest = onDismiss,
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("assembly_name_text_field"),
                    placeholder = { Text(text = "構成名称") },
                    maxLines = 1,
                    singleLine = true,
                )

            }
        },
        buttons = {
            Row (modifier = Modifier.padding(10.dp)) {
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = onDismiss) {
                    Text(text = "キャンセル")
                }
                Button(
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .testTag("create_assembly_button"),
                    onClick = { onCreationStart(assemblyName) }
                ) {
                    Text(text = "新規作成")
                }
            }
        }
    )
}