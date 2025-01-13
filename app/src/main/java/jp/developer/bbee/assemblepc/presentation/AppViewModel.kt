package jp.developer.bbee.assemblepc.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.developer.bbee.assemblepc.domain.model.Composition
import jp.developer.bbee.assemblepc.domain.use_case.ClearCurrentCompositionUseCase
import jp.developer.bbee.assemblepc.domain.use_case.GetCurrentCompositionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    getCurrentCompositionUseCase: GetCurrentCompositionUseCase,
    clearCurrentCompositionUseCase: ClearCurrentCompositionUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow<AppUiState>(AppUiState.NoSelected)
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    init {
        val job = viewModelScope.launch {
            // アプリが立ち上がった時に、空の構成が選択されている場合はクリアする
            val composition = getCurrentCompositionUseCase().first()
            if (composition != null && composition.items.isEmpty()) {
                clearCurrentCompositionUseCase()
            }
        }

        job.invokeOnCompletion {
            getCurrentCompositionUseCase()
                .onEach { composition ->
                    if (composition == null) {
                        _uiState.value = AppUiState.NoSelected
                    } else {
                        _uiState.value = AppUiState.Selected(composition)
                    }
                }
                .launchIn(viewModelScope)
        }
    }
}

sealed interface AppUiState {
    data object NoSelected: AppUiState
    data class Selected(val composition: Composition): AppUiState
}
