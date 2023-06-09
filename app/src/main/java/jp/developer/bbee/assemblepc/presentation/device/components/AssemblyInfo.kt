package jp.developer.bbee.assemblepc.presentation.device.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AssemblyInfo(
//    assemblyViewModel: AssemblyViewModel = hiltViewModel(),
    assemblyName: String,
    prices: Int,
    isNoPrice: Boolean,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val fontSize = if (screenWidth < 600) 18.sp else 24.sp
//    val assemblyName = assemblyViewModel.selectedAssemblyName.value
//    val prices = assemblyViewModel.assemblies.value.sumOf { it.devicePriceRecent }
//    val isNoPrice = assemblyViewModel.assemblies.value.any { it.devicePriceRecent == 0 }
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            modifier = Modifier.width(0.dp).weight(1f),
            text = "構成名:${assemblyName}",
            fontSize = fontSize,
            fontWeight = FontWeight.ExtraBold,
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start
        )
        // TODO: 価格のインクリメントアニメーションを追加したい
        Text(
            text = " 合計 ¥ ${"%,d".format(prices) + if (isNoPrice) "(+α)" else ""}",
            fontSize = fontSize,
            fontWeight = FontWeight.ExtraBold,
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Visible,
            textAlign = TextAlign.End
        )
    }
}