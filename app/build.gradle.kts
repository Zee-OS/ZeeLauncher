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
        versionName = "0.1.0-beta"
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
    implementation(libs.coil.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
