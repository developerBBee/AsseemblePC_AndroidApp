package jp.developer.bbee.assemblepc.presentation.screen.assembly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.developer.bbee.assemblepc.domain.model.Assembly
import jp.developer.bbee.assemblepc.domain.model.Composition
import jp.developer.bbee.assemblepc.domain.model.enums.DeviceType
import jp.developer.bbee.assemblepc.domain.use_case.DeleteItemUseCase
import jp.developer.bbee.assemblepc.domain.use_case.GetAssemblyUseCase
import jp.developer.bbee.assemblepc.domain.use_case.GetCurrentCompositionUseCase
import jp.developer.bbee.assemblepc.domain.use_case.SaveCurrentCompositionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssemblyViewModel @Inject constructor(
    getCurrentCompositionUseCase: GetCurrentCompositionUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val getAssemblyUseCase: GetAssemblyUseCase,
    private val saveCurrentCompositionUseCase: SaveCurrentCompositionUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<AssemblyUiState>(AssemblyUiState.Loading)
    val uiState: StateFlow<AssemblyUiState> = _uiState.asStateFlow()

    private val _dialogUiState = MutableStateFlow<ShowAssemblyDialog?>(null)
    val dialogUiState: StateFlow<ShowAssemblyDialog?> = _dialogUiState.asStateFlow()

    init {
        getCurrentCompositionUseCase()
            .onEach { composition ->
                _uiState.value = if (composition == null) {
                    AssemblyUiState.Error(error = "構成が設定されていません")
                } else {
                    val sortedComposition = composition
                        .copy(
                            items = composition.items
                                .sortedBy { DeviceType.from(it.deviceType).ordinal }
                        )
                    AssemblyUiState.ShowComposition(composition = sortedComposition)
                }
            }
            .launchIn(viewModelScope)
    }

    fun deleteAssembly(assembly: Assembly) {
        clearDialog()

        val state = _uiState.value as? AssemblyUiState.ShowComposition ?: return

        viewModelScope.launch {
            deleteItemUseCase(assembly)

            val assemblies = getAssemblyUseCase(assembly.assemblyId)
            val composition = state.composition.copy(items = assemblies)
            saveCurrentCompositionUseCase(composition)
        }
    }

    fun showAssemblyDialog(assembly: Assembly) {
        _dialogUiState.value = ShowAssemblyDialog(assembly)
    }

    fun clearDialog() {
        _dialogUiState.value = null
    }
}

sealed interface AssemblyUiState {
    data object Loading : AssemblyUiState
    data class ShowComposition(val composition: Composition) : AssemblyUiState
    data class Error(val error: String) : AssemblyUiState
}

data class ShowAssemblyDialog(val assembly: Assembly)
