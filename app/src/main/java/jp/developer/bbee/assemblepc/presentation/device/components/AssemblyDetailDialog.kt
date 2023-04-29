package jp.developer.bbee.assemblepc.presentation.device.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import jp.developer.bbee.assemblepc.domain.model.Assembly
import jp.developer.bbee.assemblepc.presentation.device.AssemblyViewModel

@Composable
fun AssemblyDetailDialog(
    assembly: Assembly,
    isShowDetailDialog: MutableState<Boolean>,
    assemblyViewModel: AssemblyViewModel = hiltViewModel()
) {

    AlertDialog(
        shape = RoundedCornerShape(10.dp),
        onDismissRequest = { isShowDetailDialog.value = false },
        title = { Text(text = assembly.deviceName) },
        text = {
            Column {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ){
                        DeviceImage(
                            assembly.deviceImgUrl,
                            modifier = Modifier
                                .padding(end = 10.dp, bottom = 2.dp)
                                .height(100.dp)
                                .width(100.dp)
                        )
                        Text(
                            text = if(assembly.devicePriceRecent == 0) "価格情報なし"
                                else "¥ ${assembly.devicePriceRecent}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(text = assembly.deviceDetail)
                }
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "削除",
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .clickable {
                                isShowDetailDialog.value = false
                                assemblyViewModel.deleteAssembly(assembly)
                            },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        },
        // Not used
        buttons = {}
    )
}