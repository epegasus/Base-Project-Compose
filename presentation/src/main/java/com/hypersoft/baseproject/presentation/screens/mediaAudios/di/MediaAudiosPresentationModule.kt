package com.hypersoft.baseproject.presentation.screens.mediaAudios.di

import com.hypersoft.baseproject.presentation.screens.mediaAudios.viewModel.MediaAudiosViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule

val mediaAudiosPresentationModule = lazyModule {
    viewModel { MediaAudiosViewModel(get(), get()) }
}