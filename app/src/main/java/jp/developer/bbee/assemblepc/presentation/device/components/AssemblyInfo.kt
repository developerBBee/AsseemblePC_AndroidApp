package jp.developer.bbee.assemblepc.presentation.device.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import jp.developer.bbee.assemblepc.presentation.device.AssemblyViewModel
import jp.developer.bbee.assemblepc.presentation.device.DeviceViewModel

@Composable
fun AssemblyInfo(
    assemblyViewModel: AssemblyViewModel = hiltViewModel()
) {
    Row (modifier = Modifier.fillMaxWidth()){
        Text(text = "構成名称:${assemblyViewModel.selectedAssemblyName}")
        Spacer(modifier =Modifier.weight(1f))
        Text(text = "合計金額　¥ 10,000")
    }
}