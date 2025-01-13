package jp.developer.bbee.assemblepc.presentation.screen.selection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import jp.developer.bbee.assemblepc.presentation.ScreenRoute
import jp.developer.bbee.assemblepc.presentation.navigateSingle
import jp.developer.bbee.assemblepc.presentation.screen.selection.components.VariableButtonsRow

@Composable
fun SelectionScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
        ) {
            VariableButtonsRow(
                onSelected = { navController.navigateSingle(ScreenRoute.DeviceScreen(it)) }
            )
        }
    }
}
