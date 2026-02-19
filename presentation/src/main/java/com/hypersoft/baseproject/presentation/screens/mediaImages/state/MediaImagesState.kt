package com.hypersoft.baseproject.presentation.screens.mediaImages.state

import com.hypersoft.baseproject.domain.media.entities.ImageFolderEntity
import com.hypersoft.baseproject.presentation.screens.mediaImages.enums.MediaImagesPermissionLevel

data class MediaImagesState(
    val isLoading: Boolean = false,
    val folders: List<ImageFolderEntity> = emptyList(),
    val permission: MediaImagesPermissionLevel = MediaImagesPermissionLevel.Idle
)