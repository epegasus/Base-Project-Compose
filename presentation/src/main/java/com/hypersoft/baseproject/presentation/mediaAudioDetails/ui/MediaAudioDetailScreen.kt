package com.hypersoft.baseproject.presentation.mediaAudioDetails.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hypersoft.baseproject.core.extensions.toTimeFormat
import com.hypersoft.baseproject.core.extensions.showToast
import com.hypersoft.baseproject.presentation.mediaAudioDetails.effect.MediaAudioDetailEffect
import com.hypersoft.baseproject.presentation.mediaAudioDetails.intent.MediaAudioDetailIntent
import com.hypersoft.baseproject.presentation.mediaAudioDetails.state.MediaAudioDetailState
import com.hypersoft.baseproject.presentation.mediaAudioDetails.viewModel.MediaAudioDetailViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun MediaAudioDetailScreen(
    audioUriPath: String,
    onBack: () -> Unit,
    viewModel: MediaAudioDetailViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle(MediaAudioDetailState())
    val context = LocalContext.current

    LaunchedEffect(audioUriPath) {
        viewModel.handleIntent(MediaAudioDetailIntent.LoadPlaylist(audioUriPath))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is MediaAudioDetailEffect.NavigateBack -> onBack()
                is MediaAudioDetailEffect.ShowError -> context.showToast(effect.message)
                else -> { /* Playback effects need MediaController - handled in Fragment version */ }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = { viewModel.handleIntent(MediaAudioDetailIntent.NavigateBack) },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 56.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = state.title ?: "",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = state.artist ?: "",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
            if (state.duration > 0) {
                Slider(
                    value = state.currentPosition.toFloat().coerceIn(0f, state.duration.toFloat()),
                    onValueChange = { viewModel.handleIntent(MediaAudioDetailIntent.SeekTo(it.toLong())) },
                    valueRange = 0f..state.duration.toFloat(),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            androidx.compose.foundation.layout.Row(
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(
                    text = state.currentPosition.toTimeFormat(),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = state.duration.toTimeFormat(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}
