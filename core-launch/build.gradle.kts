plugins {
    alias(libs.plugins.zee.android.library)
}

android {
    namespace = "com.zeelauncher.core_launch"
}

dependencies {
    implementation(projects.coreModel)
    implementation(projects.coreCommon)
}
