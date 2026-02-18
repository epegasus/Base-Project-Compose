package com.hypersoft.baseproject.presentation.mediaVideoDetails.ui

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hypersoft.baseproject.core.extensions.showToast
import com.hypersoft.baseproject.core.extensions.toTimeFormat
import com.hypersoft.baseproject.presentation.mediaVideoDetails.effect.MediaVideoDetailEffect
import com.hypersoft.baseproject.presentation.mediaVideoDetails.intent.MediaVideoDetailIntent
import com.hypersoft.baseproject.presentation.mediaVideoDetails.state.MediaVideoDetailState
import com.hypersoft.baseproject.presentation.mediaVideoDetails.viewModel.MediaVideoDetailViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun MediaVideoDetailScreen(
    videoUriPath: String,
    onBack: () -> Unit,
    viewModel: MediaVideoDetailViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle(MediaVideoDetailState())
    val context = LocalContext.current

    LaunchedEffect(videoUriPath) {
        viewModel.handleIntent(MediaVideoDetailIntent.LoadVideo(videoUriPath))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is MediaVideoDetailEffect.NavigateBack -> onBack()
                is MediaVideoDetailEffect.ShowError -> context.showToast(effect.message)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = { viewModel.handleIntent(MediaVideoDetailIntent.NavigateBack) },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 56.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 9f)
            ) {
                AndroidView(
                    factory = { ctx ->
                        android.widget.VideoView(ctx).apply {
                            setVideoURI(Uri.parse(videoUriPath))
                            setOnCompletionListener { viewModel.handleIntent(MediaVideoDetailIntent.PlayPause) }
                        }
                    },
                    update = { videoView ->
                        if (state.isPlaying && !videoView.isPlaying) videoView.start()
                        else if (!state.isPlaying && videoView.isPlaying) videoView.pause()
                        if (state.currentProgress > 0 && kotlin.math.abs(videoView.currentPosition - state.currentProgress) > 500) {
                            videoView.seekTo(state.currentProgress)
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
            Text(
                text = state.title ?: "",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
            Slider(
                value = state.currentProgress.toFloat(),
                onValueChange = { viewModel.handleIntent(MediaVideoDetailIntent.SeekTo(it.toInt())) },
                valueRange = 0f..(state.duration.toFloat().coerceAtLeast(1f)),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Row(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = state.currentProgress.toLong().toTimeFormat(),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = state.duration.toLong().toTimeFormat(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 8.dp)
                )
                IconButton(
                    onClick = { viewModel.handleIntent(MediaVideoDetailIntent.PlayPause) }
                ) {
                    Icon(
                        imageVector = if (state.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (state.isPlaying) "Pause" else "Play"
                    )
                }
            }
        }
    }
}
