package com.hypersoft.baseproject.presentation.screens.mediaImageDetails.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hypersoft.baseproject.presentation.mediaImageDetails.effect.MediaImageDetailEffect
import com.hypersoft.baseproject.presentation.mediaImageDetails.intent.MediaImageDetailIntent
import com.hypersoft.baseproject.presentation.mediaImageDetails.state.MediaImageDetailState
import com.hypersoft.baseproject.presentation.mediaImageDetails.viewModel.MediaImageDetailViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MediaImageDetailScreen(
    imageUriPath: String,
    onBack: () -> Unit,
    viewModel: MediaImageDetailViewModel = koinViewModel(parameters = { parametersOf(imageUriPath) })
) {
    val state by viewModel.state.collectAsStateWithLifecycle(MediaImageDetailState())

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is MediaImageDetailEffect.NavigateBack -> onBack()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(androidx.compose.material3.MaterialTheme.colorScheme.surface)
    ) {
        IconButton(
            onClick = { viewModel.handleIntent(MediaImageDetailIntent.NavigateBack) },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 56.dp),
            contentAlignment = Alignment.Center
        ) {
            if (state.imageUri.isNotEmpty()) {
                Image(
                    painter = androidx.compose.ui.res.painterResource(com.hypersoft.baseproject.core.R.drawable.img_png_placeholder),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}
