package jp.developer.bbee.assemblepc.presentation.device

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import jp.developer.bbee.assemblepc.presentation.device.components.AssemblyInfo

@Composable
fun AssemblyScreen(
    assemblyViewModel: AssemblyViewModel = hiltViewModel()
){
    val assemblies = assemblyViewModel.assemblies

    Column(modifier = Modifier.fillMaxSize()) {
        AssemblyInfo()
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(assemblies.value) { assembly ->
                Row {
                    Text(text = assembly.deviceName)
                }
            }
        }
    }
}