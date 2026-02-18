package com.hypersoft.baseproject.di

import com.hypersoft.baseproject.core.di.modules.coreModule
import com.hypersoft.baseproject.core.di.modules.dispatchersModule
import com.hypersoft.baseproject.data.di.dataSourceModules
import com.hypersoft.baseproject.data.di.repositoryModules
import com.hypersoft.baseproject.di.modules.appModule
import com.hypersoft.baseproject.domain.di.domainModule
import com.hypersoft.baseproject.presentation.screens.dashboard.di.dashboardPresentationModule
import com.hypersoft.baseproject.presentation.screens.entrance.di.entrancePresentationModule
import com.hypersoft.baseproject.presentation.screens.history.di.historyPresentationModule
import com.hypersoft.baseproject.presentation.screens.home.di.homePresentationModule
import com.hypersoft.baseproject.presentation.screens.inAppLanguage.di.inAppLanguagePresentationModule
import com.hypersoft.baseproject.presentation.screens.language.di.languagePresentationModule
import com.hypersoft.baseproject.presentation.screens.media.di.mediaPresentationModule
import com.hypersoft.baseproject.presentation.screens.mediaAudioDetails.di.mediaAudioDetailsPresentationModule
import com.hypersoft.baseproject.presentation.screens.mediaAudios.di.mediaAudiosPresentationModule
import com.hypersoft.baseproject.presentation.screens.mediaImageDetails.di.mediaImageDetailsPresentationModule
import com.hypersoft.baseproject.presentation.screens.mediaImages.di.mediaImagesPresentationModule
import com.hypersoft.baseproject.presentation.screens.mediaImagesTab.di.imagesTabPresentationModule
import com.hypersoft.baseproject.presentation.screens.mediaVideoDetails.di.mediaVideoDetailsPresentationModule
import com.hypersoft.baseproject.presentation.screens.mediaVideos.di.mediaVideosPresentationModule
import com.hypersoft.baseproject.presentation.screens.premium.di.premiumPresentationModule
import com.hypersoft.baseproject.presentation.screens.settings.di.settingsPresentationModule
import org.koin.core.module.LazyModule

class KoinModules {

    fun getKoinModules(): List<LazyModule> {
        return listOf(
            // App Modules
            appModule,

            // Core Modules
            coreModule,
            dispatchersModule,

            // Data Modules
            dataSourceModules,
            repositoryModules,

            // Domain Modules
            domainModule,

            // Presentation Modules
            entrancePresentationModule,
            languagePresentationModule,

            dashboardPresentationModule,
            homePresentationModule,
            mediaPresentationModule,
            mediaAudiosPresentationModule,
            mediaAudioDetailsPresentationModule,
            mediaVideosPresentationModule,
            mediaVideoDetailsPresentationModule,
            mediaImagesPresentationModule,
            imagesTabPresentationModule,
            mediaImageDetailsPresentationModule,
            historyPresentationModule,

            premiumPresentationModule,
            settingsPresentationModule,
            inAppLanguagePresentationModule,
        )
    }
}