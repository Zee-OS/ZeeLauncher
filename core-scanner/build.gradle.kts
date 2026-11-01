plugins {
    alias(libs.plugins.zee.android.library)
}

android {
    namespace = "com.zeelauncher.core_scanner"
}

dependencies {
    implementation(projects.coreModel)
    implementation(projects.coreCommon)
}
