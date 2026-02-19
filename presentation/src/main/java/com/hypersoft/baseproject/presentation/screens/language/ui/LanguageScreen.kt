package com.hypersoft.baseproject.presentation.screens.language.ui

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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hypersoft.baseproject.core.extensions.showToast
import com.hypersoft.baseproject.data.dataSources.inAppMemory.languages.entities.Language
import com.hypersoft.baseproject.presentation.screens.language.effect.LanguageEffect
import com.hypersoft.baseproject.presentation.screens.language.intent.LanguageIntent
import com.hypersoft.baseproject.presentation.screens.language.state.LanguageState
import com.hypersoft.baseproject.presentation.screens.language.viewModel.LanguageViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import com.hypersoft.baseproject.core.R as coreR

@Composable
fun LanguageScreen(
    onNavigateToDashboard: () -> Unit,
    viewModel: LanguageViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle(LanguageState())
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is LanguageEffect.NavigateToDashboard -> onNavigateToDashboard()
                is LanguageEffect.ShowError -> context.showToast(effect.message)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
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
                Text(
                    text = stringResource(coreR.string.choose_your_language),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Button(onClick = { viewModel.handleIntent(LanguageIntent.ApplyLanguage) }) {
                    Text(stringResource(coreR.string._continue))
                }
            }
            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = state.languages,
                        key = { it.languageCode }
                    ) { language ->
                        LanguageItem(
                            language = language,
                            onClick = { viewModel.handleIntent(LanguageIntent.SelectLanguage(language.languageCode)) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LanguageItem(
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
        border = if (language.isSelected) {
            androidx.compose.foundation.BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.primary
            )
        } else null,
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
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 16.dp)
            )
            Text(
                text = language.languageName,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .weight(1f)
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