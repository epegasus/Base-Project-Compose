package com.hypersoft.baseproject.presentation.screens.mediaImages.di

import com.hypersoft.baseproject.presentation.screens.mediaImages.viewModel.MediaImagesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule

val mediaImagesPresentationModule = lazyModule {
    viewModel { MediaImagesViewModel(get(), get()) }
}