package com.hypersoft.baseproject.presentation.screens.mediaVideoDetails.di

import com.hypersoft.baseproject.presentation.screens.mediaVideoDetails.factory.MediaPlayerFactory
import com.hypersoft.baseproject.presentation.screens.mediaVideoDetails.viewModel.MediaVideoDetailViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule

val mediaVideoDetailsPresentationModule = lazyModule {
    factory { MediaPlayerFactory(androidContext()) }
    viewModel { MediaVideoDetailViewModel(get(), get()) }
}