package com.hypersoft.baseproject.presentation.screens.inAppLanguage.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hypersoft.baseproject.core.R as coreR
import com.hypersoft.baseproject.core.extensions.showToast
import com.hypersoft.baseproject.data.dataSources.inAppMemory.languages.entities.Language
import com.hypersoft.baseproject.presentation.screens.inAppLanguage.effect.InAppLanguageEffect
import com.hypersoft.baseproject.presentation.screens.inAppLanguage.intent.InAppLanguageIntent
import com.hypersoft.baseproject.presentation.screens.inAppLanguage.state.InAppLanguageState
import com.hypersoft.baseproject.presentation.screens.inAppLanguage.viewModel.InAppLanguageViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InAppLanguageScreen(
    onBack: () -> Unit,
    viewModel: InAppLanguageViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle(InAppLanguageState())
    val context = androidx.compose.ui.platform.LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is InAppLanguageEffect.NavigateBack -> onBack()
                is InAppLanguageEffect.ShowError -> context.showToast(effect.message)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(stringResource(coreR.string.change_language)) },
            navigationIcon = {
                IconButton(onClick = { viewModel.handleIntent(InAppLanguageIntent.NavigateBack) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.languages, key = { it.languageCode }) { language ->
                        InAppLanguageItem(
                            language = language,
                            onClick = { viewModel.handleIntent(InAppLanguageIntent.SelectLanguage(language.languageCode)) }
                        )
                    }
                }
                Button(
                    onClick = { viewModel.handleIntent(InAppLanguageIntent.ApplyLanguage) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text(stringResource(coreR.string._continue))
                }
            }
        }
    }
}

@Composable
private fun InAppLanguageItem(
    language: Language,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (language.isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.foundation.Image(
                painter = painterResource(language.flagIcon),
                contentDescription = null
            )
            Text(
                text = language.languageName,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            )
            if (language.isSelected) {
                androidx.compose.foundation.Image(
                    painter = painterResource(coreR.drawable.ic_language_done),
                    contentDescription = null
                )
            }
        }
    }
}

