package com.hypersoft.baseproject.presentation.screens.entrance.effect

sealed class EntranceEffect {
    object NavigateToLanguage : EntranceEffect()
    object NavigateToDashboard : EntranceEffect()
    data class ShowError(val message: String) : EntranceEffect()
}