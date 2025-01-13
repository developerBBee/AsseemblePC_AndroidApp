package jp.developer.bbee.assemblepc.presentation.screen.assembly

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.developer.bbee.assemblepc.domain.model.Assembly
import jp.developer.bbee.assemblepc.presentation.screen.assembly.components.AssemblyDetailDialog
import jp.developer.bbee.assemblepc.presentation.screen.assembly.components.AssemblyRow

@Composable
fun AssemblyScreen(
    assemblyViewModel: AssemblyViewModel = hiltViewModel()
){

    val uiState by assemblyViewModel.uiState.collectAsStateWithLifecycle()
    val dialogUiState by assemblyViewModel.dialogUiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        AssemblyUiState.Loading -> {
            CircularProgressIndicator()
        }

        is AssemblyUiState.Error -> {
            Text(text = state.error)
        }

        is AssemblyUiState.ShowComposition -> {
            AssemblyContent(
                modifier = Modifier.fillMaxSize(),
                assemblies = state.composition.items,
                onAssemblyClick = { assemblyViewModel.showAssemblyDialog(it) }
            )
        }
    }

    dialogUiState?.let { state ->
        AssemblyDetailDialog(
            assembly = state.assembly,
            onDismiss = { assemblyViewModel.clearDialog() },
            onDeleteClick = { assemblyViewModel.deleteAssembly(state.assembly) }
        )
    }
}

@Composable
private fun AssemblyContent(
    modifier: Modifier = Modifier,
    assemblies: List<Assembly>,
    onAssemblyClick: (Assembly) -> Unit
) {

    Column(modifier = modifier) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(assemblies) { assembly ->
                AssemblyRow(
                    modifier = Modifier.padding(10.dp),
                    assembly = assembly,
                    onAssemblyClick = { onAssemblyClick(assembly) }
                )
            }
        }
    }
}
