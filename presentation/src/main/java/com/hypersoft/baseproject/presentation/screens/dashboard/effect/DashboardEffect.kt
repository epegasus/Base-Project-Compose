package com.hypersoft.baseproject.presentation.screens.dashboard.effect

sealed class DashboardEffect {
    object RegisterBackPress : DashboardEffect()
    data class ShowError(val message: String) : DashboardEffect()
}