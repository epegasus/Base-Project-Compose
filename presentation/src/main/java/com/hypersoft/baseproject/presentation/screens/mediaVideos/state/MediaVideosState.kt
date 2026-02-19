package com.hypersoft.baseproject.presentation.screens.mediaVideos.state

import com.hypersoft.baseproject.domain.media.entities.VideoEntity
import com.hypersoft.baseproject.presentation.screens.mediaVideos.enums.MediaVideosPermissionLevel

data class MediaVideosState(
    val isLoading: Boolean = false,
    val videos: List<VideoEntity> = emptyList(),
    val permission: MediaVideosPermissionLevel = MediaVideosPermissionLevel.Idle
)