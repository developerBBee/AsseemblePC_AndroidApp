package jp.developer.bbee.assemblepc.presentation.device.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import jp.developer.bbee.assemblepc.presentation.device.DeviceViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DeviceSearchText(
    deviceViewModel: DeviceViewModel = hiltViewModel()
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        Modifier.height(60.dp)) {
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = deviceViewModel.searchText.value,
            label = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "検索バー"
                )
            },
            onValueChange = {
                deviceViewModel.setSearchText(it)
            },
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                // Enterで実行でソフトウェアキーボードを隠す
                keyboardController?.hide()
            })
        )
    }
}