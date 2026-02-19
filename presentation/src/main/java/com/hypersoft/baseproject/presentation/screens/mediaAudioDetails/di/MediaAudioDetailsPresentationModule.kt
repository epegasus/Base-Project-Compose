package com.hypersoft.baseproject.presentation.screens.mediaAudioDetails.di

import com.hypersoft.baseproject.presentation.screens.mediaAudioDetails.viewModel.MediaAudioDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule

val mediaAudioDetailsPresentationModule = lazyModule {
    viewModel { MediaAudioDetailViewModel(get()) }
}