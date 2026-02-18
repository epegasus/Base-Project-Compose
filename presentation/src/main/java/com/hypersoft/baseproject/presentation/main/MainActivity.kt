package com.hypersoft.baseproject.presentation.main

import androidx.compose.ui.platform.ComposeView
import com.hypersoft.baseproject.presentation.base.activity.BaseActivity
import com.hypersoft.baseproject.presentation.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onPreCreated() {
        installSplashTheme()
        enableMaterialDynamicTheme()
        hideStatusBar(1)
    }

    override fun onCreated() {
        (binding.root as ComposeView).setContent {
            App(activity = this)
        }
    }
}