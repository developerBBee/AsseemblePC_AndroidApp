package jp.developer.bbee.assemblepc.presentation.top

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.developer.bbee.assemblepc.domain.model.Assembly
import jp.developer.bbee.assemblepc.domain.use_case.GetAllAssemblyUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopViewModel @Inject constructor(
    private val getAllAssemblyUseCase: GetAllAssemblyUseCase,
    // navigate()のrouteパラメータを受け取るためのSavedStateHandle
    savedStateHandle: SavedStateHandle
    ) : ViewModel() {
    val showDialogState = mutableStateOf(false)
    var selectedAssemblyId by mutableStateOf<Int?>(null)
    var allAssemblyMap: Map<Int, List<Assembly>> by mutableStateOf(mapOf())

    init {
        savedStateHandle.get<String>("show")?.let {
            showDialogState.value = true
        }
        getAllAssembly()
    }
    private fun getAllAssembly() {
        viewModelScope.launch {
            val allAssembly = getAllAssemblyUseCase()
            // assemblyIdごとにAssemblyをまとめる
            allAssemblyMap = allAssembly.groupBy { it.assemblyId }
        }
    }
}