package com.hypersoft.baseproject.core.permission

import android.Manifest
import android.os.Build
import com.hypersoft.baseproject.core.permission.enums.MediaPermission

/**
 * Returns permission strings for the given [MediaPermission] based on Android version.
 * Used by Compose screens that request permissions via ActivityResultLauncher.
 */
fun MediaPermission.permissionStrings(): Array<String> {
    return when (this) {
        MediaPermission.IMAGES_VIDEOS -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO)
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        MediaPermission.AUDIOS -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(Manifest.permission.READ_MEDIA_AUDIO)
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
}
