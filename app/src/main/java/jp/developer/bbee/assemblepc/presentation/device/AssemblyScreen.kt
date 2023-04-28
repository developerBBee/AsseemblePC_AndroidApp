package jp.developer.bbee.assemblepc.presentation.device

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import jp.developer.bbee.assemblepc.presentation.device.components.AssemblyInfo
import jp.developer.bbee.assemblepc.presentation.device.components.AssemblyRow

@Composable
fun AssemblyScreen(
    assemblyViewModel: AssemblyViewModel = hiltViewModel()
){
    val assemblies = assemblyViewModel.assemblies

    Column(
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
    ) {
        AssemblyInfo()
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(assemblies.value) { assembly ->
                AssemblyRow(assembly)
            }
        }
    }
}