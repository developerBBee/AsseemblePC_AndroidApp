package jp.developer.bbee.assemblepc.presentation.screen.top

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.developer.bbee.assemblepc.domain.model.Composition
import jp.developer.bbee.assemblepc.domain.use_case.DeleteAssemblyUseCase
import jp.developer.bbee.assemblepc.domain.use_case.GetAllAssemblyUseCase
import jp.developer.bbee.assemblepc.domain.use_case.GetMaxAssemblyIdUseCase
import jp.developer.bbee.assemblepc.domain.use_case.RenameAssemblyUseCase
import jp.developer.bbee.assemblepc.domain.use_case.SaveCurrentCompositionUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopViewModel @Inject constructor(
    private val getAllAssemblyUseCase: GetAllAssemblyUseCase,
    private val deleteAssemblyUseCase: DeleteAssemblyUseCase,
    private val renameAssemblyUseCase: RenameAssemblyUseCase,
    private val getMaxAssemblyIdUseCase: GetMaxAssemblyIdUseCase,
    private val saveCurrentCompositionUseCase: SaveCurrentCompositionUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<TopUiState>(TopUiState.Loading)
    val uiState: StateFlow<TopUiState> = _uiState.asStateFlow()

    private val _dialogUiState = MutableStateFlow<TopDialogUiState?>(null)
    val dialogUiState: StateFlow<TopDialogUiState?> = _dialogUiState.asStateFlow()

    private val _navFlow = MutableSharedFlow<TopSideEffect>()
    val navFlow: SharedFlow<TopSideEffect> = _navFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            getAllAssembly()
        }
    }

    private suspend fun getAllAssembly() {
        val allAssembly = getAllAssemblyUseCase()

        // assemblyIdごとにAssemblyをまとめる
        val allComposition = allAssembly.groupBy { it.assemblyId }
            .map { (assemblyId, items) ->
                Composition(assemblyId, items[0].assemblyName, items)
            }

        _uiState.value = TopUiState.Loaded(allComposition)
    }

    fun showCreateDialog() {
        _dialogUiState.value = TopDialogUiState.ShowCreate
    }

    fun selectComposition(composition: Composition) {
        _dialogUiState.value = TopDialogUiState.ShowEdit(composition)
    }

    fun showRenameConfirm(
        composition: Composition,
        newName: String
    ) {
        _dialogUiState.value = TopDialogUiState.ShowRenameConfirm(composition, newName)
    }

    fun renameAssembly(newName: String, assemblyId: Int) {
        closeDialog()
        viewModelScope.launch {
            renameAssemblyUseCase(newName, assemblyId)
            getAllAssembly()
        }
    }

    fun showDeleteConfirm(composition: Composition) {
        _dialogUiState.value = TopDialogUiState.ShowDeleteConfirm(composition)
    }

    fun deleteAssembly(
        assemblyId: Int,
        onComplete: () -> Unit
    ) {
        closeDialog()
        viewModelScope.launch {
            deleteAssemblyUseCase(assemblyId)
            getAllAssembly()
            onComplete()
        }
    }

    fun createNewComposition(assemblyName: String) {
        closeDialog()
        viewModelScope.launch {
            val id = (getMaxAssemblyIdUseCase() ?: 0) + 1
            val newComposition = Composition(
                assemblyId = id,
                assemblyName = assemblyName,
            )
            saveCurrentCompositionUseCase(newComposition)
            _navFlow.emit(TopSideEffect.NEW_CREATION)
        }
    }

    fun addParts(targetComposition: Composition) {
        closeDialog()
        viewModelScope.launch {
            saveCurrentCompositionUseCase(targetComposition)
            _navFlow.emit(TopSideEffect.ADD_PARTS)
        }
    }

    fun showComposition(targetComposition: Composition) {
        closeDialog()
        viewModelScope.launch {
            saveCurrentCompositionUseCase(targetComposition)
            _navFlow.emit(TopSideEffect.SHOW_COMPOSITION)
        }
    }

    fun closeDialog() {
        _dialogUiState.value = null
    }
}

sealed interface TopUiState {
    data object Loading : TopUiState
    data class Loaded(val allComposition: List<Composition>) : TopUiState
}

sealed interface TopDialogUiState {
    data object ShowCreate : TopDialogUiState
    data class ShowEdit(val compo: Composition) : TopDialogUiState
    data class ShowRenameConfirm(val compo: Composition, val newName: String) : TopDialogUiState
    data class ShowDeleteConfirm(val compo: Composition) : TopDialogUiState
}

enum class TopSideEffect {
    NEW_CREATION,
    ADD_PARTS,
    SHOW_COMPOSITION,
}
