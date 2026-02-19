package com.hypersoft.baseproject.presentation.screens.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hypersoft.baseproject.core.di.DIComponent
import com.hypersoft.baseproject.presentation.R
import com.hypersoft.baseproject.presentation.screens.home.effect.HomeEffect
import com.hypersoft.baseproject.presentation.screens.home.intent.HomeIntent
import com.hypersoft.baseproject.presentation.screens.home.state.HomeState
import com.hypersoft.baseproject.presentation.screens.home.viewModel.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle(HomeState())
    val diComponent = DIComponent()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            /*when (effect) {
                is HomeEffect.NavigateToDrawer ->
                    diComponent.generalObserver.navigate(R.id.action_dashboardFragment_to_drawerFragment)
                is HomeEffect.NavigateToPremium ->
                    diComponent.generalObserver.navigate(R.id.action_global_premiumFragment)
                is HomeEffect.NavigateToMedia ->
                    diComponent.generalObserver.navigate(R.id.action_dashboardFragment_to_nav_graph_media)
            }*/
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.handleIntent(HomeIntent.DrawerClicked) }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Drawer"
                )
            }
            Text(
                text = "Base Project Compose",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            FilledIconButton(onClick = { viewModel.handleIntent(HomeIntent.PremiumClicked) }) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Premium"
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { viewModel.handleIntent(HomeIntent.MediaClicked) }) {
                Text("Media")
            }
        }
    }
}

