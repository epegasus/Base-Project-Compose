package com.hypersoft.baseproject.presentation.screens.dashboard.di

import com.hypersoft.baseproject.presentation.screens.dashboard.viewModel.DashboardViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule

val dashboardPresentationModule = lazyModule {
    viewModel { DashboardViewModel() }
}