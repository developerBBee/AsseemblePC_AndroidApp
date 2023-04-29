package jp.developer.bbee.assemblepc.presentation.top

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import jp.developer.bbee.assemblepc.presentation.ScreenRoute
import jp.developer.bbee.assemblepc.presentation.top.components.AssemblyThumbnail
import jp.developer.bbee.assemblepc.presentation.top.components.CreateAssemblyDialog

@Composable
fun TopScreen(
    navController: NavController,
    topViewModel: TopViewModel = hiltViewModel()
) {
    val showDialogState = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Temporary code
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(topViewModel.allAssemblyList) {
                AssemblyThumbnail(
                    assemblies = it,
                    onClick = {
                        navController.navigate(
                            ScreenRoute.AssemblyScreen.route
                                    + "/${it[0].assemblyId}"
                                    + "/${it[0].assemblyName}"
                                    + "/pccase"
                        )
                    }
                )
            }
            if (topViewModel.allAssemblyList.size > 2) {
                item { Spacer(modifier = Modifier.height(50.dp)) }
            }
        }
        Button(
            onClick = { showDialogState.value = true },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFe0ffff)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(vertical = 10.dp)
        ) {
            Text(
                text = "構成を新規作成",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xff2f4f4f)
            )
        }
    }
    if (showDialogState.value) {
        CreateAssemblyDialog(navController, showDialogState)
    }
}