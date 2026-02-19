package com.hypersoft.baseproject.presentation.screens.mediaImages.intent

import com.hypersoft.baseproject.presentation.screens.mediaImages.enums.MediaImagesPermissionLevel

sealed class MediaImagesIntent {
    object NavigationBack : MediaImagesIntent()
    object LoadFolders : MediaImagesIntent()
    object RefreshFolders : MediaImagesIntent()
    object GrantPermissionClick : MediaImagesIntent()
    data class PermissionChanged(val level: MediaImagesPermissionLevel) : MediaImagesIntent()
    data class ImageClicked(val imageUri: String) : MediaImagesIntent()
}