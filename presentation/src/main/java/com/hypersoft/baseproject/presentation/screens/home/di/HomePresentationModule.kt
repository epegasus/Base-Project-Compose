package com.hypersoft.baseproject.presentation.screens.home.di

import com.hypersoft.baseproject.presentation.screens.home.viewModel.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule

val homePresentationModule = lazyModule {
    viewModel { HomeViewModel() }
}