package com.hypersoft.baseproject.presentation.navigations.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hypersoft.baseproject.presentation.navigations.routes.Route
import com.hypersoft.baseproject.presentation.screens.media.ui.MediaScreen
import com.hypersoft.baseproject.presentation.screens.mediaAudioDetails.ui.MediaAudioDetailScreen
import com.hypersoft.baseproject.presentation.screens.mediaAudios.ui.MediaAudiosScreen
import com.hypersoft.baseproject.presentation.screens.mediaImageDetails.ui.MediaImageDetailScreen
import com.hypersoft.baseproject.presentation.screens.mediaImages.ui.MediaImagesScreen
import com.hypersoft.baseproject.presentation.screens.mediaVideoDetails.ui.MediaVideoDetailScreen
import com.hypersoft.baseproject.presentation.screens.mediaVideos.ui.MediaVideosScreen

fun NavGraphBuilder.mediaGraph(nav: NavHostController) {

    composable(Route.Media.route) {
        MediaScreen(
            onBack = { nav.popBackStack() },
            onNavigateToAudios = { nav.navigate(Route.MediaAudios.route) },
            onNavigateToImages = { nav.navigate(Route.MediaImages.route) },
            onNavigateToVideos = { nav.navigate(Route.MediaVideos.route) }
        )
    }

    composable(Route.MediaAudios.route) {
        MediaAudiosScreen(
            onBack = { nav.popBackStack() },
            onAudioClick = {
                nav.navigate(Route.MediaAudioDetail.create(it))
            }
        )
    }

    composable(
        Route.MediaAudioDetail.route,
        arguments = listOf(navArgument("audioUriPath") { type = NavType.StringType })
    ) {
        val uri = it.arguments?.getString("audioUriPath")!!
        MediaAudioDetailScreen(uri, onBack = { nav.popBackStack() })
    }

    composable(Route.MediaImages.route) {
        MediaImagesScreen(
            onBack = { nav.popBackStack() },
            onImageClick = {
                nav.navigate(Route.MediaImageDetail.create(it))
            }
        )
    }

    composable(
        Route.MediaImageDetail.route,
        arguments = listOf(navArgument("imageUriPath") { type = NavType.StringType })
    ) {
        val uri = it.arguments?.getString("imageUriPath")!!
        MediaImageDetailScreen(uri, onBack = { nav.popBackStack() })
    }

    composable(Route.MediaVideos.route) {
        MediaVideosScreen(
            onBack = { nav.popBackStack() },
            onVideoClick = {
                nav.navigate(Route.MediaVideoDetail.create(it))
            }
        )
    }

    composable(
        Route.MediaVideoDetail.route,
        arguments = listOf(navArgument("videoUriPath") { type = NavType.StringType })
    ) {
        val uri = it.arguments?.getString("videoUriPath")!!
        MediaVideoDetailScreen(uri, onBack = { nav.popBackStack() })
    }
}