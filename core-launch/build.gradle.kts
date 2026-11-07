plugins {
    alias(libs.plugins.zee.android.library)
    alias(libs.plugins.zee.hilt)
}

android {
    namespace = "com.zeelauncher.core_launch"
}

dependencies {
    api(projects.coreModel)
    implementation(projects.coreCommon)
}
