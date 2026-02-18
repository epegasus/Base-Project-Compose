package com.hypersoft.baseproject.presentation.screens.mediaAudios.intent

sealed class MediaAudiosIntent {
    object LoadAudios : MediaAudiosIntent()
    data class AudioClicked(val audioUri: String) : MediaAudiosIntent()
}