package com.hypersoft.baseproject.presentation.screens.mediaVideoDetails.intent

sealed class MediaVideoDetailIntent {
    object PlayPause : MediaVideoDetailIntent()
    object NavigateBack : MediaVideoDetailIntent()
    data class LoadVideo(val videoUri: String) : MediaVideoDetailIntent()
    data class SeekTo(val position: Int) : MediaVideoDetailIntent()
}