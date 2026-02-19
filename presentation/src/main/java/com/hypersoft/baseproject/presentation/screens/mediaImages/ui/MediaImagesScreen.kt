package com.hypersoft.baseproject.presentation.screens.mediaImages.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hypersoft.baseproject.core.extensions.showToast
import com.hypersoft.baseproject.presentation.screens.mediaImages.effect.MediaImagesEffect
import com.hypersoft.baseproject.presentation.screens.mediaImages.intent.MediaImagesIntent
import com.hypersoft.baseproject.presentation.screens.mediaImages.state.MediaImagesState
import com.hypersoft.baseproject.presentation.screens.mediaImages.viewModel.MediaImagesViewModel
import com.hypersoft.baseproject.presentation.screens.mediaImagesTab.ui.ImagesTabContent
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaImagesScreen(
    onBack: () -> Unit,
    onImageClick: (String) -> Unit,
    viewModel: MediaImagesViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle(MediaImagesState())
    val context = LocalContext.current
    var selectedTab by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.handleIntent(MediaImagesIntent.PermissionChanged(com.hypersoft.baseproject.presentation.screens.mediaImages.enums.MediaImagesPermissionLevel.Full))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is MediaImagesEffect.NavigateBack -> onBack()
                is MediaImagesEffect.GrantPermissionClick -> { /* open settings */ }
                is MediaImagesEffect.NavigateToDetail -> onImageClick(effect.imageUri)
                is MediaImagesEffect.ShowError -> context.showToast(effect.message)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        IconButton(onClick = { viewModel.handleIntent(MediaImagesIntent.NavigationBack) }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Text(
            text = stringResource(com.hypersoft.baseproject.core.R.string.images),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (state.folders.isNotEmpty()) {
            TabRow(selectedTabIndex = selectedTab) {
                state.folders.forEachIndexed { index, folder ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text("${folder.folderName} (${folder.imageCount})") }
                    )
                }
            }
            Box(modifier = Modifier.weight(1f)) {
                val folderName = state.folders.getOrNull(selectedTab)?.folderName
                if (folderName != null) {
                    ImagesTabContent(
                        folderName = folderName,
                        onImageClick = { uri -> viewModel.handleIntent(MediaImagesIntent.ImageClicked(uri)) }
                    )
                }
            }
        } else {
            Text(
                text = "No folders",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
