package com.hypersoft.baseproject.presentation.screens.history.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hypersoft.baseproject.core.R as coreR
import com.hypersoft.baseproject.core.extensions.showToast
import com.hypersoft.baseproject.presentation.screens.history.effect.HistoryEffect
import com.hypersoft.baseproject.presentation.screens.history.state.HistoryState
import com.hypersoft.baseproject.presentation.screens.history.viewModel.HistoryViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle(HistoryState())
    val context = androidx.compose.ui.platform.LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is HistoryEffect.ShowError -> context.showToast(effect.message)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    Text(
                        text = stringResource(coreR.string.history),
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                items(state.histories, key = { it.title }) { history ->
                    Text(
                        text = history.title,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

