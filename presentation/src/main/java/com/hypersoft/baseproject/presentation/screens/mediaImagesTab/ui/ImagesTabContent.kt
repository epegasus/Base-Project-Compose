package com.hypersoft.baseproject.presentation.screens.mediaImagesTab.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hypersoft.baseproject.core.extensions.showToast
import com.hypersoft.baseproject.domain.media.entities.ImageEntity
import com.hypersoft.baseproject.presentation.screens.mediaImagesTab.effect.ImagesTabEffect
import com.hypersoft.baseproject.presentation.screens.mediaImagesTab.intent.ImagesTabIntent
import com.hypersoft.baseproject.presentation.screens.mediaImagesTab.state.ImagesTabState
import com.hypersoft.baseproject.presentation.screens.mediaImagesTab.viewModel.ImagesTabViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ImagesTabContent(
    folderName: String,
    onImageClick: (String) -> Unit,
    viewModel: ImagesTabViewModel = koinViewModel(parameters = { parametersOf(folderName) })
) {
    val state by viewModel.state.collectAsStateWithLifecycle(ImagesTabState())
    val context = androidx.compose.ui.platform.LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is ImagesTabEffect.NavigateToDetail ->
                    onImageClick(effect.imageUri)
                is ImagesTabEffect.ShowError ->
                    context.showToast(effect.message)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(120.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.images, key = { it.uri.toString() }) { image ->
                    ImageItem(
                        image = image,
                        onClick = { viewModel.handleIntent(ImagesTabIntent.ImageClicked(image.uri.toString())) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ImageItem(
    image: ImageEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHighest)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = androidx.compose.ui.res.painterResource(com.hypersoft.baseproject.core.R.drawable.img_png_placeholder),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

