package com.hypersoft.baseproject.presentation.screens.mediaAudios.effect

sealed class MediaAudiosEffect {
    data class NavigateToDetail(val audioUri: String) : MediaAudiosEffect()
    data class ShowError(val message: String) : MediaAudiosEffect()
}