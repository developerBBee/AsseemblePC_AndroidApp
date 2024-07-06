package jp.developer.bbee.assemblepc.presentation.selection.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.DeviceScreen
import jp.developer.bbee.assemblepc.presentation.device.AssemblyViewModel
import jp.developer.bbee.assemblepc.presentation.selection.deviceTypes

@Composable
fun VariableButtonsRow (
    navController: NavController,
    assemblyViewModel: AssemblyViewModel,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    // 画面幅340dpごとに列数を増やすレスポンシブレイアウト
    val rows = (screenWidth / 340).toInt() + 1 // toInt()は不要だが、整数明示のため
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        for (i in 0 .. deviceTypes.size / rows) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                for (j in 0 until rows) {
                    // (i+1)行(j+1)列目のデバイスのボタンを生成、存在しなければnull->Spacer
                    val type = deviceTypes.getOrNull(i * rows + j)
                    if (type != null) {
                        Button(
                            modifier = Modifier
                                .width(160.dp)
                                .height(120.dp)
                                .clip(shape = RoundedCornerShape(20.dp)),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.primary,
                            ),
                            onClick = {
                                navController.navigate(
                                    DeviceScreen.route
                                    + "/${assemblyViewModel.selectedAssemblyId}"
                                    + "/${assemblyViewModel.selectedAssemblyName.value.replace("/", "燬／")}"
                                    + "/${type.getOrDefault("path", "")}"
                                )
                            }
                        ) {
                            val text = type.getOrDefault("text", "")
                            val textSize = if (text.replace("\n","").length > 5) 16.sp else 20.sp
                            Text(
                                text = text,
                                fontSize = textSize,
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.width(160.dp))
                    }
                }
            }
        }
    }
}