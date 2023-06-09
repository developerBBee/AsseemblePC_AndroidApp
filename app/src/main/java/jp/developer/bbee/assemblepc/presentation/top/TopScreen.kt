package jp.developer.bbee.assemblepc.presentation.top

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import jp.developer.bbee.assemblepc.R
import jp.developer.bbee.assemblepc.presentation.device.AssemblyViewModel
import jp.developer.bbee.assemblepc.presentation.device.components.AssemblyInfo
import jp.developer.bbee.assemblepc.presentation.top.components.AssemblyThumbnail
import jp.developer.bbee.assemblepc.presentation.top.components.CreateAssemblyDialog
import jp.developer.bbee.assemblepc.presentation.top.components.DeleteAssemblyConfirmDialog
import jp.developer.bbee.assemblepc.presentation.top.components.EditAssemblyDialog
import jp.developer.bbee.assemblepc.presentation.top.components.RenameAssemblyConfirmDialog

@Composable
fun TopScreen(
    navController: NavController,
    topViewModel: TopViewModel = hiltViewModel(),
    assemblyViewModel: AssemblyViewModel = hiltViewModel()
) {
    Column(Modifier.fillMaxSize()) {
        val name = topViewModel.getAssemblyName(assemblyViewModel.selectedAssemblyId)
        if (name != null)
            AssemblyInfo(
                assemblyName = name,
                prices = assemblyViewModel.assemblies.value.sumOf { it.devicePriceRecent },
                isNoPrice = assemblyViewModel.assemblies.value.any { it.devicePriceRecent == 0 }
            )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            val allList = topViewModel.allAssemblyMap.values.toList()
            if (allList.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(0.dp)
                            .weight(3f),
                        painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(0.dp).weight(1f))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "「構成を新規作成」をタップして開始",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(0.dp).weight(1f))
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(allList) {
                    AssemblyThumbnail(
                        assemblies = it,
                        onClick = {
                            topViewModel.selectAssembly(it[0].assemblyId)
                        }
                    )
                }
                if (allList.size > 2) {
                    item { Spacer(modifier = Modifier.height(50.dp)) }
                }
            }
            Button(
                onClick = { topViewModel.showCreateDialog = true },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFe0ffff)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
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
    if (topViewModel.showCreateDialog) {
        CreateAssemblyDialog(navController, topViewModel)
    } else if (topViewModel.selectedAssemblyId != null) {
        if (topViewModel.deleteConfirm) {
            DeleteAssemblyConfirmDialog(navController, topViewModel)
        } else if (topViewModel.renameConfirm) {
            RenameAssemblyConfirmDialog(topViewModel)
        } else {
            EditAssemblyDialog(navController, topViewModel)
        }
    }
}