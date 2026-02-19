package com.hypersoft.baseproject.presentation.screens.mediaVideos.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hypersoft.baseproject.core.extensions.showToast
import com.hypersoft.baseproject.core.extensions.toTimeFormat
import com.hypersoft.baseproject.domain.media.entities.VideoEntity
import com.hypersoft.baseproject.presentation.screens.mediaVideos.effect.MediaVideosEffect
import com.hypersoft.baseproject.presentation.screens.mediaVideos.intent.MediaVideosIntent
import com.hypersoft.baseproject.presentation.screens.mediaVideos.state.MediaVideosState
import com.hypersoft.baseproject.presentation.screens.mediaVideos.viewModel.MediaVideosViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun MediaVideosScreen(
    onBack: () -> Unit,
    onVideoClick: (String) -> Unit,
    viewModel: MediaVideosViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle(MediaVideosState())
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.handleIntent(MediaVideosIntent.PermissionChanged(com.hypersoft.baseproject.presentation.screens.mediaVideos.enums.MediaVideosPermissionLevel.Full))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is MediaVideosEffect.NavigateBack -> onBack()
                is MediaVideosEffect.GrantPermissionClick -> { }
                is MediaVideosEffect.NavigateToDetail -> onVideoClick(effect.videoUri)
                is MediaVideosEffect.ShowError -> context.showToast(effect.message)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        IconButton(onClick = { viewModel.handleIntent(MediaVideosIntent.NavigationBack) }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Text(
            text = stringResource(com.hypersoft.baseproject.core.R.string.videos),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.videos, key = { it.id }) { video ->
                        VideoItem(
                            video = video,
                            onClick = { viewModel.handleIntent(MediaVideosIntent.VideoClicked(video.uri.toString())) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun VideoItem(
    video: VideoEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHighest)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = video.displayName.ifEmpty { "Unknown" },
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = video.duration.toTimeFormat(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
