package com.hypersoft.baseproject.presentation.screens.media.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.hypersoft.baseproject.core.R as coreR
import com.hypersoft.baseproject.core.extensions.showToast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.hypersoft.baseproject.core.permission.enums.MediaPermission
import com.hypersoft.baseproject.core.permission.permissionStrings
import com.hypersoft.baseproject.presentation.R
import com.hypersoft.baseproject.presentation.media.effect.MediaEffect
import com.hypersoft.baseproject.presentation.media.intent.MediaIntent
import com.hypersoft.baseproject.presentation.media.state.MediaState
import com.hypersoft.baseproject.presentation.media.viewModel.MediaViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun MediaScreen(
    onBack: () -> Unit,
    onNavigateToAudios: () -> Unit,
    onNavigateToImages: () -> Unit,
    onNavigateToVideos: () -> Unit,
    viewModel: MediaViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle(MediaState())
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is MediaEffect.NavigateBack -> onBack()
                is MediaEffect.NavigateToImages -> onNavigateToImages()
                is MediaEffect.NavigateToVideos -> onNavigateToVideos()
                is MediaEffect.NavigateToAudios -> onNavigateToAudios()
                is MediaEffect.ShowError -> context.showToast(effect.message)
            }
        }
    }

    val activity = context as? androidx.activity.ComponentActivity
    val audioLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { resultMap ->
        if (resultMap.values.any { it }) viewModel.handleIntent(MediaIntent.NavigateToAudios)
        else context.showToast(context.getString(coreR.string.permission_denied))
    }
    val imagesLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { resultMap ->
        if (resultMap.values.any { it }) viewModel.handleIntent(MediaIntent.NavigateToImages)
        else context.showToast(context.getString(coreR.string.permission_denied))
    }
    val videosLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { resultMap ->
        if (resultMap.values.any { it }) viewModel.handleIntent(MediaIntent.NavigateToVideos)
        else context.showToast(context.getString(coreR.string.permission_denied))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        IconButton(onClick = { viewModel.handleIntent(MediaIntent.NavigateBack) }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    val perms = MediaPermission.AUDIOS.permissionStrings()
                    if (perms.all { ContextCompat.checkSelfPermission(context, it) == android.content.pm.PackageManager.PERMISSION_GRANTED }) {
                        viewModel.handleIntent(MediaIntent.NavigateToAudios)
                    } else {
                        activity?.let { audioLauncher.launch(perms) }
                            ?: context.showToast(context.getString(coreR.string.permission_denied))
                    }
                }
            ) {
                Text(stringResource(coreR.string.audios))
            }
            Button(
                onClick = {
                    val perms = MediaPermission.IMAGES_VIDEOS.permissionStrings()
                    if (perms.all { ContextCompat.checkSelfPermission(context, it) == android.content.pm.PackageManager.PERMISSION_GRANTED }) {
                        viewModel.handleIntent(MediaIntent.NavigateToImages)
                    } else {
                        activity?.let { imagesLauncher.launch(perms) }
                            ?: context.showToast(context.getString(coreR.string.permission_denied))
                    }
                },
                modifier = Modifier.padding(top = 24.dp)
            ) {
                Text(stringResource(coreR.string.images))
            }
            Button(
                onClick = {
                    val perms = MediaPermission.IMAGES_VIDEOS.permissionStrings()
                    if (perms.all { ContextCompat.checkSelfPermission(context, it) == android.content.pm.PackageManager.PERMISSION_GRANTED }) {
                        viewModel.handleIntent(MediaIntent.NavigateToVideos)
                    } else {
                        activity?.let { videosLauncher.launch(perms) }
                            ?: context.showToast(context.getString(coreR.string.permission_denied))
                    }
                },
                modifier = Modifier.padding(top = 24.dp)
            ) {
                Text(stringResource(coreR.string.videos))
            }
        }
    }
}
