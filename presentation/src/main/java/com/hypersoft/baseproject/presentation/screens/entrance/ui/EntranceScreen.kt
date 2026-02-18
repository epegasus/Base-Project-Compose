package com.hypersoft.baseproject.presentation.screens.entrance.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hypersoft.baseproject.core.extensions.showToast
import com.hypersoft.baseproject.presentation.entrance.effect.EntranceEffect
import com.hypersoft.baseproject.presentation.entrance.state.EntranceState
import com.hypersoft.baseproject.presentation.entrance.viewModel.EntranceViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun EntranceScreen(
    onNavigateToLanguage: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    viewModel: EntranceViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle(EntranceState())
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is EntranceEffect.NavigateToLanguage -> onNavigateToLanguage()
                is EntranceEffect.NavigateToDashboard -> onNavigateToDashboard()
                is EntranceEffect.ShowError -> context.showToast(effect.message)
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Base Project Compose",
            style = MaterialTheme.typography.headlineMedium
        )
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) {
                CircularProgressIndicator(strokeWidth = 4.dp)
            }
        }
    }
}
