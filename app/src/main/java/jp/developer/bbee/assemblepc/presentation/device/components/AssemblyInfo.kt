package jp.developer.bbee.assemblepc.presentation.device.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import jp.developer.bbee.assemblepc.presentation.device.AssemblyViewModel

@Composable
fun AssemblyInfo(
    assemblyViewModel: AssemblyViewModel = hiltViewModel()
) {
    val prices = assemblyViewModel.assemblies.value.sumOf { it.devicePriceRecent }
    val isNoPrice = assemblyViewModel.assemblies.value.any { it.devicePriceRecent == 0 }
    Row (modifier = Modifier.fillMaxWidth()){
        Text(text = "構成名称:${assemblyViewModel.selectedAssemblyName.value}")
        Spacer(modifier =Modifier.weight(1f))
        // TODO: 価格のインクリメントアニメーションを追加したい
        Text(text = "合計金額　¥ ${"%,d".format(prices) + if (isNoPrice) "(＋α)" else ""}")
    }
}