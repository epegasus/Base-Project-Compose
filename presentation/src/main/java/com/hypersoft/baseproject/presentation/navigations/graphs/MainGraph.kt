package com.hypersoft.baseproject.presentation.navigations.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.hypersoft.baseproject.presentation.navigations.routes.Route
import com.hypersoft.baseproject.presentation.screens.dashboard.ui.DashboardScreen
import com.hypersoft.baseproject.presentation.screens.drawer.DrawerScreen
import com.hypersoft.baseproject.presentation.screens.entrance.ui.EntranceScreen
import com.hypersoft.baseproject.presentation.screens.inAppLanguage.ui.InAppLanguageScreen
import com.hypersoft.baseproject.presentation.screens.language.ui.LanguageScreen
import com.hypersoft.baseproject.presentation.screens.premium.ui.PremiumScreen

fun NavGraphBuilder.mainGraph(nav: NavHostController) {

    composable(Route.Entrance.route) {
        EntranceScreen(
            onNavigateToLanguage = { nav.navigate(Route.Language.route) },
            onNavigateToDashboard = { nav.navigate(Route.Dashboard.route) }
        )
    }

    composable(Route.Language.route) {
        LanguageScreen { nav.navigate(Route.Dashboard.route) }
    }

    composable(Route.Dashboard.route) {
        DashboardScreen(
            navController = nav,
            onShowExitDialog = {},
            viewModel = {}
        )
    }

    composable(Route.Drawer.route) {
        DrawerScreen(onBack = { nav.popBackStack() })
    }

    composable(Route.Premium.route) {
        PremiumScreen(onBack = { nav.popBackStack() })
    }

    composable(Route.InAppLanguage.route) {
        InAppLanguageScreen(onBack = { nav.popBackStack() })
    }
}