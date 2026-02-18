package com.hypersoft.baseproject.presentation.screens.premium.di

import com.hypersoft.baseproject.presentation.premium.viewModel.PremiumViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule

val premiumPresentationModule = lazyModule {
    viewModel { PremiumViewModel() }
}