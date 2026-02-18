package com.hypersoft.baseproject.presentation.dashboard.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import android.app.Activity
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hypersoft.baseproject.core.extensions.showToast
import com.hypersoft.baseproject.presentation.dashboard.effect.DashboardEffect
import com.hypersoft.baseproject.presentation.dashboard.intent.DashboardIntent
import com.hypersoft.baseproject.presentation.dashboard.viewModel.DashboardViewModel
import com.hypersoft.baseproject.presentation.history.ui.HistoryScreen
import com.hypersoft.baseproject.presentation.home.ui.HomeScreen
import com.hypersoft.baseproject.presentation.navigation.NavRoutes
import com.hypersoft.baseproject.presentation.settings.ui.SettingsScreen
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

sealed class DashboardDest(val route: String, val title: String, val icon: ImageVector) {
    data object Home : DashboardDest(NavRoutes.HOME, "Home", Icons.Default.Home)
    data object History : DashboardDest(NavRoutes.HISTORY, "History", Icons.Default.History)
    data object Settings : DashboardDest(NavRoutes.SETTINGS, "Settings", Icons.Default.Settings)
}

val dashboardDestinations = listOf(
    DashboardDest.Home,
    DashboardDest.History,
    DashboardDest.Settings
)

@Composable
fun DashboardScreen(
    navController: NavHostController,
    onShowExitDialog: () -> Unit,
    viewModel: DashboardViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var showExitDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is DashboardEffect.RegisterBackPress -> { /* BackHandler handles back */ }
                is DashboardEffect.ShowError -> context.showToast(effect.message)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.handleIntent(DashboardIntent.RegisterBackPress)
    }

    val dashboardNavController = rememberNavController()
    val navBackStackEntry by dashboardNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BackHandler {
        if (currentDestination?.route == NavRoutes.HOME) {
            showExitDialog = true
        } else {
            dashboardNavController.popBackStack()
        }
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text(context.getString(com.hypersoft.baseproject.core.R.string.exit_message)) },
            confirmButton = {
                TextButton(onClick = {
                    showExitDialog = false
                    (context as? android.app.Activity)?.finish()
                }) {
                    Text(context.getString(com.hypersoft.baseproject.core.R.string.exit))
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text(context.getString(com.hypersoft.baseproject.core.R.string.cancel))
                }
            }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                dashboardDestinations.forEach { dest ->
                    val selected = currentDestination?.hierarchy?.any { it.route == dest.route } == true
                    NavigationBarItem(
                        icon = { Icon(dest.icon, contentDescription = dest.title) },
                        label = { Text(dest.title) },
                        selected = selected,
                        onClick = {
                            dashboardNavController.navigate(dest.route) {
                                popUpTo(dashboardNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = dashboardNavController,
            startDestination = NavRoutes.HOME,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            composable(NavRoutes.HOME) {
                HomeScreen()
            }
            composable(NavRoutes.HISTORY) {
                HistoryScreen()
            }
            composable(NavRoutes.SETTINGS) {
                SettingsScreen()
            }
        }
    }
}
