plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.hypersoft.baseproject"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.hypersoft.baseproject"
        minSdk = 24
        targetSdk = 36
        versionCode = 3
        versionName = "3.0.0-MVI"
    }

    base {
        archivesName = "Base-Project-v${defaultConfig.versionCode}(${defaultConfig.versionName})"
    }

    // Use the "release" signing configuration for the release build
    /*signingConfigs {
        create("release") {
            storeFile = file("D:\\User\\keyStore\\BaseProject.jks")
            storePassword = "BaseProject-Compose"
            keyAlias = "BaseProject-Compose"
            keyPassword = "BaseProject-Compose"
        }
    }*/

    buildTypes {
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        release {
            // Use the "release" signing configuration for the release build
            // signingConfig = signingConfigs.getByName("release")

            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
        compose = true
    }

    // Disable language-specific APK splits, include all languages in a single APK
    bundle {
        language {
            enableSplit = false
        }
    }
}

dependencies {
    // Modules
    implementation(project(":core"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":presentation"))

    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)

    // Dependency Injection -> Koin
    implementation(libs.koin.android)
    implementation(libs.koin.core.coroutines)
}