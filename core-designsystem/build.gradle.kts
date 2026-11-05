plugins {
    alias(libs.plugins.zee.android.library)
    alias(libs.plugins.zee.android.compose)
}

android {
    namespace = "com.zeelauncher.core_designsystem"
}

dependencies {
    api(projects.coreCommon)
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.ui.graphics)
    api(libs.androidx.compose.material3)
}
