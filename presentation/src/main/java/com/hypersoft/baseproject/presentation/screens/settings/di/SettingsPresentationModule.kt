package com.hypersoft.baseproject.presentation.screens.settings.di

import com.hypersoft.baseproject.presentation.screens.settings.viewModel.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule

val settingsPresentationModule = lazyModule {
    viewModel { SettingsViewModel(get()) }
}