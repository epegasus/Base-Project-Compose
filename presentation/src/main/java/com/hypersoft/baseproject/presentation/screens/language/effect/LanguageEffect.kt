package com.hypersoft.baseproject.presentation.screens.language.effect

sealed class LanguageEffect {
    object NavigateToDashboard : LanguageEffect()
    data class ShowError(val message: String) : LanguageEffect()
}