package jp.developer.bbee.assemblepc.presentation.top.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.TopScreen
import jp.developer.bbee.assemblepc.presentation.device.AssemblyViewModel
import jp.developer.bbee.assemblepc.presentation.top.TopViewModel

@Composable
fun DeleteAssemblyConfirmDialog(
    navController: NavController,
    topViewModel: TopViewModel,
    assemblyViewModel: AssemblyViewModel = hiltViewModel()
) {
    val selectedId = topViewModel.selectedAssemblyId ?: return
    val selectedList = topViewModel.allAssemblyMap[selectedId] ?: return
    val selectedName = selectedList[0].assemblyName

    val context = LocalContext.current
    val toast = Toast.makeText(context, "${selectedName}を削除しました", Toast.LENGTH_SHORT)

    AlertDialog(
        shape = RoundedCornerShape(10.dp),
        onDismissRequest = {
            topViewModel.closeEditDialog()
        },
        title = {
            Column {
                Text(
                    text = selectedName,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                )
                Text(
                    text = "　の削除確認",
                    fontSize = 16.sp,
                )
            }
        },
        text = {
            Column {
                Text(
                    text = "構成を削除しますか？",
                    fontWeight = FontWeight.Bold
                )
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
                    onClick = {
                        topViewModel.closeEditDialog()
                    }
                ) {
                    Text(text = "キャンセル")
                }
                Button(
                    onClick = {
                        topViewModel.deleteAssembly(selectedId, toast)
                        if (assemblyViewModel.selectedAssemblyId == selectedId) {
                            navController.navigate(TopScreen.route)
                        }
                    }
                ) {
                    Text(text = "構成を削除")
                }
            }
        }
    )
}