package com.hypersoft.baseproject.presentation.screens.home.intent

sealed class HomeIntent {
    object DrawerClicked : HomeIntent()
    object PremiumClicked : HomeIntent()
    object MediaClicked : HomeIntent()
}