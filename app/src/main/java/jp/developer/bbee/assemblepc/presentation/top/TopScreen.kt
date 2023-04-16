package jp.developer.bbee.assemblepc.presentation.top

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import jp.developer.bbee.assemblepc.presentation.top.components.AssemblyThumbnail
import jp.developer.bbee.assemblepc.presentation.top.components.CreateAssemblyDialog

@Composable
fun TopScreen(
    navController: NavController
) {
    val showDialogState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Temporary code
        LazyColumn(
            modifier = Modifier.weight(0.8f).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(1) {
                AssemblyThumbnail(
                    assemblies = emptyList(),
                    onClick = { }
                )
            }
        }
        Column(
            modifier = Modifier.weight(0.2f).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { showDialogState.value = true },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFe0ffff)
                ),
                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
            ) {
                Text(
                    text = "構成を新規作成",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff2f4f4f)
                )
            }
        }
    }
    if (showDialogState.value) {
        CreateAssemblyDialog(navController, showDialogState)
    }
}