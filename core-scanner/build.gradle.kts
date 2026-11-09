plugins {
    alias(libs.plugins.zee.android.library)
    alias(libs.plugins.zee.hilt)
}

android {
    namespace = "com.zeelauncher.core_scanner"
}

dependencies {
    api(projects.coreModel)
    api(libs.coil.core)
    implementation(projects.coreCommon)
    implementation(libs.androidx.core.ktx)
}
