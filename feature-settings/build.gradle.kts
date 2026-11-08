plugins {
    alias(libs.plugins.zee.android.feature)
}

android {
    namespace = "com.zeelauncher.feature_settings"
}

dependencies {
    implementation(libs.androidx.activity.compose)
}