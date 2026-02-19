package com.hypersoft.baseproject.presentation.navigations.routes

/**
 * Central navigation routes for Compose Navigation.
 */
sealed class Route(val route: String) {

    object Entrance : Route("entrance")
    object Language : Route("language")
    object Dashboard : Route("dashboard")
    object Drawer : Route("drawer")
    object Premium : Route("premium")
    object InAppLanguage : Route("in_app_language")

    object Media : Route("media")
    object MediaAudios : Route("media_audios")
    object MediaImages : Route("media_images")
    object MediaVideos : Route("media_videos")

    object MediaAudioDetail : Route("media_audio_detail/{audioUriPath}") {
        fun create(audioUriPath: String) = "media_audio_detail/$audioUriPath"
    }

    object MediaImageDetail : Route("media_image_detail/{imageUriPath}") {
        fun create(imageUriPath: String) = "media_image_detail/$imageUriPath"
    }

    object MediaVideoDetail : Route("media_video_detail/{videoUriPath}") {
        fun create(videoUriPath: String) = "media_video_detail/$videoUriPath"
    }
}