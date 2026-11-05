plugins {
    alias(libs.plugins.zee.android.library)
    alias(libs.plugins.zee.android.compose)
}

android {
    namespace = "com.zeelauncher.core_navigation"
}

dependencies {
    api(libs.androidx.navigation.compose)
    api(libs.kotlinx.serialization.core)
}
