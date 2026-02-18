package com.hypersoft.baseproject.presentation.screens.settings.effect

sealed class SettingsEffect {
    object NavigateToLanguage : SettingsEffect()
    object GiveFeedback : SettingsEffect()
    object ShowRateUsDialog : SettingsEffect()
    object ShareApp : SettingsEffect()
    object OpenPrivacyPolicy : SettingsEffect()
    data class ShowError(val message: String) : SettingsEffect()
}