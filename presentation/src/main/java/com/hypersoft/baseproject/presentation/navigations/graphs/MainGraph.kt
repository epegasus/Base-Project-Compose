package com.hypersoft.baseproject.presentation.navigations.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hypersoft.baseproject.presentation.navigations.routes.Route
import com.hypersoft.baseproject.presentation.screens.dashboard.ui.DashboardScreen
import com.hypersoft.baseproject.presentation.screens.drawer.DrawerScreen
import com.hypersoft.baseproject.presentation.screens.entrance.ui.EntranceScreen
import com.hypersoft.baseproject.presentation.screens.inAppLanguage.ui.InAppLanguageScreen
import com.hypersoft.baseproject.presentation.screens.language.ui.LanguageScreen
import com.hypersoft.baseproject.presentation.screens.premium.ui.PremiumScreen

fun NavGraphBuilder.mainGraph(navController: NavController) {

    composable(Route.Entrance.route) {
        EntranceScreen(
            onNavigateToLanguage = { navController.navigate(Route.Language.route) },
            onNavigateToDashboard = { navController.navigate(Route.Dashboard.route) }
        )
    }

    composable(Route.Language.route) {
        LanguageScreen(onNavigateToDashboard = {
            navController.navigate(Route.Dashboard.route)
        })
    }

    composable(Route.Dashboard.route) {
        DashboardScreen(
            navController = navController,
            onShowExitDialog = {}
        )
    }

    composable(Route.Drawer.route) {
        DrawerScreen(onBack = { navController.popBackStack() })
    }

    composable(Route.Premium.route) {
        PremiumScreen(onBack = { navController.popBackStack() })
    }

    composable(Route.InAppLanguage.route) {
        InAppLanguageScreen(onBack = { navController.popBackStack() })
    }
}