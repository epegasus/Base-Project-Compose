package com.hypersoft.baseproject.presentation.screens.mediaImagesTab.effect

sealed class ImagesTabEffect {
    data class NavigateToDetail(val imageUri: String) : ImagesTabEffect()
    data class ShowError(val message: String) : ImagesTabEffect()
}