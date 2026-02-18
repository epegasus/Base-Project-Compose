package com.hypersoft.baseproject.presentation.navigation

/**
 * Central navigation routes for Compose Navigation.
 * Mirrors the original nav_graph structure.
 */
object NavRoutes {

    const val ENTRANCE = "entrance"
    const val LANGUAGE = "language"
    const val DASHBOARD = "dashboard"
    const val DRAWER = "drawer"
    const val PREMIUM = "premium"
    const val IN_APP_LANGUAGE = "in_app_language"

    // Dashboard bottom nav
    const val HOME = "home"
    const val HISTORY = "history"
    const val SETTINGS = "settings"

    // Media graph
    const val MEDIA = "media"
    const val MEDIA_AUDIOS = "media_audios"
    const val MEDIA_AUDIO_DETAIL = "media_audio_detail/{audioUriPath}"
    const val MEDIA_IMAGES = "media_images"
    const val MEDIA_IMAGE_DETAIL = "media_image_detail/{imageUriPath}"
    const val MEDIA_VIDEOS = "media_videos"
    const val MEDIA_VIDEO_DETAIL = "media_video_detail/{videoUriPath}"

    fun mediaAudioDetail(audioUriPath: String) = "media_audio_detail/$audioUriPath"
    fun mediaImageDetail(imageUriPath: String) = "media_image_detail/$imageUriPath"
    fun mediaVideoDetail(videoUriPath: String) = "media_video_detail/$videoUriPath"
}
