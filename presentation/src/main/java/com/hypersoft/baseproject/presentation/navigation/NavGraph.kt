package com.hypersoft.baseproject.presentation.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hypersoft.baseproject.core.di.DIComponent
import com.hypersoft.baseproject.presentation.R
import com.hypersoft.baseproject.presentation.dashboard.ui.DashboardScreen
import com.hypersoft.baseproject.presentation.drawer.DrawerScreen
import com.hypersoft.baseproject.presentation.entrance.ui.EntranceScreen
import com.hypersoft.baseproject.presentation.inAppLanguage.ui.InAppLanguageScreen
import com.hypersoft.baseproject.presentation.language.ui.LanguageScreen
import com.hypersoft.baseproject.presentation.media.ui.MediaScreen
import com.hypersoft.baseproject.presentation.mediaAudioDetails.ui.MediaAudioDetailScreen
import com.hypersoft.baseproject.presentation.mediaAudios.ui.MediaAudiosScreen
import com.hypersoft.baseproject.presentation.mediaImageDetails.ui.MediaImageDetailScreen
import com.hypersoft.baseproject.presentation.mediaImages.ui.MediaImagesScreen
import com.hypersoft.baseproject.presentation.mediaVideoDetails.ui.MediaVideoDetailScreen
import com.hypersoft.baseproject.presentation.mediaVideos.ui.MediaVideosScreen
import com.hypersoft.baseproject.presentation.premium.ui.PremiumScreen

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    onDestinationChange: (String) -> Unit = {}
) {
    val diComponent = DIComponent()
    val generalObserver = diComponent.generalObserver

    BackHandler {
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        if (currentRoute != NavRoutes.ENTRANCE && currentRoute != NavRoutes.LANGUAGE) {
            navController.popBackStack()
        }
    }

    // Observe navigation triggered by ViewModels via GeneralObserver
    val navId by generalObserver.navigateById.observeAsState()
    LaunchedEffect(navId) {
        navId?.let { id ->
            val route = when (id) {
                R.id.action_entranceFragment_to_languageFragment -> NavRoutes.LANGUAGE
                R.id.action_entranceFragment_to_dashboardFragment -> NavRoutes.DASHBOARD
                R.id.action_languageFragment_to_dashboardFragment -> NavRoutes.DASHBOARD
                R.id.action_dashboardFragment_to_drawerFragment -> NavRoutes.DRAWER
                R.id.action_dashboardFragment_to_nav_graph_media -> NavRoutes.MEDIA
                R.id.action_dashboardFragment_to_inAppLanguageFragment -> NavRoutes.IN_APP_LANGUAGE
                R.id.action_global_premiumFragment -> NavRoutes.PREMIUM
                else -> null
            }
            route?.let { navController.navigate(it) }
        }
    }

    NavHost(
        navController = navController,
        startDestination = NavRoutes.ENTRANCE
    ) {
        composable(NavRoutes.ENTRANCE) {
            EntranceScreen(
                onNavigateToLanguage = { navController.navigate(NavRoutes.LANGUAGE) },
                onNavigateToDashboard = { navController.navigate(NavRoutes.DASHBOARD) }
            )
        }

        composable(NavRoutes.LANGUAGE) {
            LanguageScreen(
                onNavigateToDashboard = { navController.navigate(NavRoutes.DASHBOARD) }
            )
        }

        composable(NavRoutes.DASHBOARD) {
            DashboardScreen(
                navController = navController,
                onShowExitDialog = { /* handled in DashboardScreen */ }
            )
        }

        composable(NavRoutes.DRAWER) {
            DrawerScreen(onBack = { navController.popBackStack() })
        }

        composable(NavRoutes.PREMIUM) {
            PremiumScreen(onBack = { navController.popBackStack() })
        }

        composable(NavRoutes.IN_APP_LANGUAGE) {
            InAppLanguageScreen(onBack = { navController.popBackStack() })
        }

        composable(NavRoutes.MEDIA) {
            MediaScreen(
                onBack = { navController.popBackStack() },
                onNavigateToAudios = { navController.navigate(NavRoutes.MEDIA_AUDIOS) },
                onNavigateToImages = { navController.navigate(NavRoutes.MEDIA_IMAGES) },
                onNavigateToVideos = { navController.navigate(NavRoutes.MEDIA_VIDEOS) }
            )
        }

        composable(NavRoutes.MEDIA_AUDIOS) {
            MediaAudiosScreen(
                onBack = { navController.popBackStack() },
                onAudioClick = { uri -> navController.navigate(NavRoutes.mediaAudioDetail(uri)) }
            )
        }

        composable(
            route = NavRoutes.MEDIA_AUDIO_DETAIL,
            arguments = listOf(navArgument("audioUriPath") { type = androidx.navigation.NavType.StringType })
        ) { backStackEntry ->
            val audioUriPath = backStackEntry.arguments?.getString("audioUriPath") ?: return@composable
            MediaAudioDetailScreen(
                audioUriPath = audioUriPath,
                onBack = { navController.popBackStack() }
            )
        }

        composable(NavRoutes.MEDIA_IMAGES) {
            MediaImagesScreen(
                onBack = { navController.popBackStack() },
                onImageClick = { uri -> navController.navigate(NavRoutes.mediaImageDetail(uri)) }
            )
        }

        composable(
            route = NavRoutes.MEDIA_IMAGE_DETAIL,
            arguments = listOf(navArgument("imageUriPath") { type = androidx.navigation.NavType.StringType })
        ) { backStackEntry ->
            val imageUriPath = backStackEntry.arguments?.getString("imageUriPath") ?: return@composable
            MediaImageDetailScreen(
                imageUriPath = imageUriPath,
                onBack = { navController.popBackStack() }
            )
        }

        composable(NavRoutes.MEDIA_VIDEOS) {
            MediaVideosScreen(
                onBack = { navController.popBackStack() },
                onVideoClick = { uri -> navController.navigate(NavRoutes.mediaVideoDetail(uri)) }
            )
        }

        composable(
            route = NavRoutes.MEDIA_VIDEO_DETAIL,
            arguments = listOf(navArgument("videoUriPath") { type = androidx.navigation.NavType.StringType })
        ) { backStackEntry ->
            val videoUriPath = backStackEntry.arguments?.getString("videoUriPath") ?: return@composable
            MediaVideoDetailScreen(
                videoUriPath = videoUriPath,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
