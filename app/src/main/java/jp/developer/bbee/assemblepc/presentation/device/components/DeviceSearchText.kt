package jp.developer.bbee.assemblepc.presentation.device.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import jp.developer.bbee.assemblepc.presentation.device.DeviceViewModel

@Composable
fun DeviceSearchText(
    modifier: Modifier = Modifier,
    deviceViewModel: DeviceViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    var hasFocus by remember { mutableStateOf(false) }
    var entered by remember { mutableStateOf(false) }
    val focusOrEntered = hasFocus || entered

    Box(modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier.fillMaxSize()
                .onFocusChanged { hasFocus = it.isFocused },
            value = deviceViewModel.searchText.value,
            label = {
                Row {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "検索バー"
                    )
                    if (!focusOrEntered) {
                        Text(text = "検索")
                    }
                }
            },
            onValueChange = {
                deviceViewModel.setSearchText(it)
                entered = it.isNotEmpty()
            },
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                // Enterで実行でFocusを外してソフトウェアキーボードを隠す
                focusManager.clearFocus()
            })
        )
        SortMenu()
    }
}