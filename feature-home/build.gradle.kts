plugins {
    alias(libs.plugins.zee.android.library)
    alias(libs.plugins.zee.android.compose)
}

android {
    namespace = "com.zeelauncher.feature_home"
}

dependencies {
    implementation(projects.coreCommon)
    implementation(projects.coreDesignsystem)
    implementation(projects.coreInput)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
}
