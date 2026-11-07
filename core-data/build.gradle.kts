plugins {
    alias(libs.plugins.zee.android.library)
    alias(libs.plugins.zee.hilt)
}

android {
    namespace = "com.zeelauncher.core_data"
}

dependencies {
    api(projects.coreModel)
    implementation(projects.coreCommon)
    implementation(projects.coreDatabase)
    implementation(projects.coreDatastore)
    implementation(projects.coreScanner)
    implementation(projects.coreLaunch)
}
