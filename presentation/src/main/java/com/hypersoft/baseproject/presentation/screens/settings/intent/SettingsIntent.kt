package com.hypersoft.baseproject.presentation.screens.settings.intent

sealed class SettingsIntent {
    object LoadVersionName : SettingsIntent()
    object LanguageClicked : SettingsIntent()
    object FeedbackClicked : SettingsIntent()
    object RateUsClicked : SettingsIntent()
    object ShareAppClicked : SettingsIntent()
    object PrivacyPolicyClicked : SettingsIntent()
}