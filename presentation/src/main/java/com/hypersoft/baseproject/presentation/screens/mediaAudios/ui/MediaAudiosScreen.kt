package com.hypersoft.baseproject.presentation.screens.mediaAudios.ui

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hypersoft.baseproject.core.R
import com.hypersoft.baseproject.core.extensions.showToast
import com.hypersoft.baseproject.core.extensions.toTimeFormat
import com.hypersoft.baseproject.domain.media.entities.AudioEntity
import com.hypersoft.baseproject.presentation.mediaAudios.effect.MediaAudiosEffect
import com.hypersoft.baseproject.presentation.mediaAudios.intent.MediaAudiosIntent
import com.hypersoft.baseproject.presentation.mediaAudios.state.MediaAudiosState
import com.hypersoft.baseproject.presentation.mediaAudios.viewModel.MediaAudiosViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun MediaAudiosScreen(
    onBack: () -> Unit,
    onAudioClick: (String) -> Unit,
    viewModel: MediaAudiosViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle(MediaAudiosState())
    val context = androidx.compose.ui.platform.LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is MediaAudiosEffect.NavigateToDetail -> onAudioClick(effect.audioUri)
                is MediaAudiosEffect.ShowError -> context.showToast(effect.message)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        IconButton(onClick = { onBack() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Text(
            text = "Audios",
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
                    items(state.audios, key = { it.id }) { audio ->
                        AudioItem(
                            audio = audio,
                            onClick = { viewModel.handleIntent(MediaAudiosIntent.AudioClicked(audio.uri.toString())) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AudioItem(
    audio: AudioEntity,
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
            androidx.compose.foundation.Image(
                painter = painterResource(R.drawable.ic_svg_audio),
                contentDescription = null,
                modifier = Modifier.padding(end = 16.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = audio.displayName.ifEmpty { "Unknown" },
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = audio.artist.ifEmpty { "Unknown Artist" },
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Text(
                text = audio.duration.toTimeFormat(),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

