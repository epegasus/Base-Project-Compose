package com.hypersoft.baseproject.presentation.navigations

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.hypersoft.baseproject.presentation.navigations.graphs.mainGraph
import com.hypersoft.baseproject.presentation.navigations.graphs.mediaGraph
import com.hypersoft.baseproject.presentation.navigations.routes.Route

@Composable
fun AppNavGraph(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Entrance.route,
        modifier = modifier
    ) {
        mainGraph(navController)
        mediaGraph(navController)
    }
}