package com.hypersoft.baseproject.presentation.screens.history.effect

sealed class HistoryEffect {
    data class ShowError(val message: String) : HistoryEffect()
}