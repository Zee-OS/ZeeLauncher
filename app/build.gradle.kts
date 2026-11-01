plugins {
    alias(libs.plugins.zee.android.application)
    alias(libs.plugins.zee.android.compose)
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
    implementation(projects.coreDesignsystem)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
