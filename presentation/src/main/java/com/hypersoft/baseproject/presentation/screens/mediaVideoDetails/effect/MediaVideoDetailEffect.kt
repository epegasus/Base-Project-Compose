package com.hypersoft.baseproject.presentation.screens.mediaVideoDetails.effect

sealed class MediaVideoDetailEffect {
    object NavigateBack : MediaVideoDetailEffect()
    data class ShowError(val message: String) : MediaVideoDetailEffect()
}