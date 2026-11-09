plugins {
    alias(libs.plugins.zee.android.application)
    alias(libs.plugins.zee.android.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.zee.hilt)
}

android {
    namespace = "com.dwiaziprasetya.zeelauncher"

    defaultConfig {
        applicationId = "com.dwiaziprasetya.zeelauncher"
        versionCode = 1
        versionName = "1.0.0"
    }

    bundle {
        language {
            enableSplit = false
        }
    }

    signingConfigs {
        create("release") {
            val storePath = providers.gradleProperty("ZEE_UPLOAD_STORE_FILE")
            if (storePath.isPresent) {
                storeFile = file(storePath.get())
                storePassword = providers.gradleProperty("ZEE_UPLOAD_STORE_PASSWORD").get()
                keyAlias = providers.gradleProperty("ZEE_UPLOAD_KEY_ALIAS").get()
                keyPassword = providers.gradleProperty("ZEE_UPLOAD_KEY_PASSWORD").get()
            }
        }
    }

    buildTypes {
        release {
            if (providers.gradleProperty("ZEE_UPLOAD_STORE_FILE").isPresent) {
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }
}

dependencies {
    implementation(projects.coreCommon)
    implementation(projects.coreDesignsystem)
    implementation(projects.coreInput)
    implementation(projects.coreNavigation)
    implementation(projects.featureLibrary)
    implementation(projects.featureApps)
    implementation(projects.featureSettings)
    implementation(projects.coreScanner)
    implementation(projects.coreDatabase)
    implementation(projects.coreData)

    implementation(libs.coil.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.splashscreen)
}
