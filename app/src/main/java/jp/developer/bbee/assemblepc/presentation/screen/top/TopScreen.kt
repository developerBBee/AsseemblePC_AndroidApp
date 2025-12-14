package jp.developer.bbee.assemblepc.presentation.screen.top

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import jp.developer.bbee.assemblepc.R
import jp.developer.bbee.assemblepc.domain.model.Composition
import jp.developer.bbee.assemblepc.presentation.ScreenRoute
import jp.developer.bbee.assemblepc.presentation.navigateSingle
import jp.developer.bbee.assemblepc.presentation.screen.top.components.AssemblyThumbnail
import jp.developer.bbee.assemblepc.presentation.screen.top.components.CreateAssemblyDialog
import jp.developer.bbee.assemblepc.presentation.screen.top.components.DeleteAssemblyConfirmDialog
import jp.developer.bbee.assemblepc.presentation.screen.top.components.EditAssemblyDialog
import jp.developer.bbee.assemblepc.presentation.screen.top.components.RenameAssemblyConfirmDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TopScreen(
    navController: NavController,
    scope: CoroutineScope,
    topViewModel: TopViewModel = hiltViewModel(),
) {
    val uiState by topViewModel.uiState.collectAsStateWithLifecycle()
    val dialogUiState by topViewModel.dialogUiState.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        val job = scope.launch {
            topViewModel.navFlow.collect { sideEffect ->
                val route = when (sideEffect) {
                    TopSideEffect.NEW_CREATION,
                    TopSideEffect.ADD_PARTS -> ScreenRoute.SelectionScreen

                    TopSideEffect.SHOW_COMPOSITION -> ScreenRoute.AssemblyScreen
                }
                navController.navigateSingle(route)
            }
        }

        onDispose {
            job.cancel()
        }
    }

    when (val state = uiState) {
        TopUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is TopUiState.Loaded -> {
            TopScreenContent(
                allComposition = state.allComposition,
                onCompositionClick = { topViewModel.selectComposition(it) },
                onStartButtonClick = { topViewModel.showCreateDialog() }
            )
        }

        is TopUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = state.error ?: "エラーが発生しました")
            }
        }
    }

    when (val state = dialogUiState) {
        is TopDialogUiState.ShowCreate -> {
            CreateAssemblyDialog(
                onDismiss = { topViewModel.clearDialog() },
                onCreationStart = { name -> topViewModel.createNewComposition(name) }
            )
        }

        is TopDialogUiState.ShowEdit -> {
            val composition = state.compo
            EditAssemblyDialog(
                selectedName = composition.assemblyName,
                onDismiss = { topViewModel.clearDialog() },
                onAddParts = { topViewModel.addParts(composition) },
                onShowComposition = { topViewModel.showComposition(composition) },
                onRenameClick = { newName -> topViewModel.showRenameConfirm(composition, newName) },
                onDeleteClick = { topViewModel.showDeleteConfirm(composition) }
            )
        }

        is TopDialogUiState.ShowRenameConfirm -> {
            val selectedName = state.compo.assemblyName
            val newName = state.newName
            val assemblyId = state.compo.assemblyId
            RenameAssemblyConfirmDialog(
                selectedName = selectedName,
                newName = newName,
                onDismiss = { topViewModel.clearDialog() },
                onConfirm = { topViewModel.renameAssembly(newName, assemblyId) }
            )
        }

        is TopDialogUiState.ShowDeleteConfirm -> {
            val context = LocalContext.current
            val selectedName = state.compo.assemblyName
            DeleteAssemblyConfirmDialog(
                selectedName = selectedName,
                onDismiss = { topViewModel.clearDialog() },
                onConfirm = {
                    topViewModel.deleteAssembly(state.compo.assemblyId) {
                        Toast.makeText(
                            context,
                            "${selectedName}を削除しました",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
        }

        null -> Unit
    }
}

@Composable
private fun TopScreenContent(
    allComposition: List<Composition>,
    onCompositionClick: (Composition) -> Unit,
    onStartButtonClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (allComposition.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.dp)
                        .weight(3f),
                    painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(0.dp).weight(1f))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "「構成を新規作成」をタップして開始",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(0.dp).weight(1f))
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(allComposition) { composition ->
                AssemblyThumbnail(
                    composition = composition,
                    onClick = { onCompositionClick(composition) }
                )
            }
            if (allComposition.size > 2) {
                item { Spacer(modifier = Modifier.height(50.dp)) }
            }
        }

        Button(
            onClick = onStartButtonClick,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Text(
                modifier = Modifier.testTag("start_button"),
                text = "構成を新規作成",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}