package com.hypersoft.baseproject.presentation.screens.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hypersoft.baseproject.core.R as coreR
import com.hypersoft.baseproject.core.extensions.openEmailApp
import com.hypersoft.baseproject.core.extensions.openPlayStoreApp
import com.hypersoft.baseproject.core.extensions.openWebUrl
import com.hypersoft.baseproject.core.extensions.shareApp
import com.hypersoft.baseproject.core.extensions.showToast
import com.hypersoft.baseproject.presentation.screens.settings.effect.SettingsEffect
import com.hypersoft.baseproject.presentation.screens.settings.intent.SettingsIntent
import com.hypersoft.baseproject.presentation.screens.settings.state.SettingsState
import com.hypersoft.baseproject.presentation.screens.settings.viewModel.SettingsViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle(SettingsState())
    val context = LocalContext.current
    val diComponent = com.hypersoft.baseproject.core.di.DIComponent()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            /*when (effect) {
                is SettingsEffect.NavigateToLanguage -> diComponent.generalObserver.navigate(com.hypersoft.baseproject.presentation.R.id.action_dashboardFragment_to_inAppLanguageFragment)
                is SettingsEffect.GiveFeedback -> context.openEmailApp(coreR.string.app_email)
                is SettingsEffect.ShowRateUsDialog -> context.openPlayStoreApp()
                is SettingsEffect.ShareApp -> context.shareApp()
                is SettingsEffect.OpenPrivacyPolicy -> context.openWebUrl(coreR.string.app_privacy_policy_link)
                is SettingsEffect.ShowError -> context.showToast(effect.message)
            }*/
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(coreR.string.settings),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(0.9f),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHighest),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                SettingsRow(stringResource(coreR.string.app_language)) {
                    viewModel.handleIntent(SettingsIntent.LanguageClicked)
                }
                SettingsRow(stringResource(coreR.string.feedback)) {
                    viewModel.handleIntent(SettingsIntent.FeedbackClicked)
                }
                SettingsRow(stringResource(coreR.string.rate_us)) {
                    viewModel.handleIntent(SettingsIntent.RateUsClicked)
                }
                SettingsRow(stringResource(coreR.string.share_app)) {
                    viewModel.handleIntent(SettingsIntent.ShareAppClicked)
                }
                SettingsRow(stringResource(coreR.string.privacy_policy)) {
                    viewModel.handleIntent(SettingsIntent.PrivacyPolicyClicked)
                }
                Text(
                    text = context.getString(coreR.string.version_s, state.versionName ?: ""),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
private fun SettingsRow(
    title: String,
    onClick: () -> Unit
) {
    Text(
        text = title,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    )
}
