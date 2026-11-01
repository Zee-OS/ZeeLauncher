plugins {
    alias(libs.plugins.zee.android.library)
    alias(libs.plugins.zee.android.compose)
}

android {
    namespace = "com.zeelauncher.core_input"
}

dependencies {
    implementation(libs.androidx.compose.ui)
}
