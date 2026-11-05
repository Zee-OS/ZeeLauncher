plugins {
    alias(libs.plugins.zee.android.library)
}

android {
    namespace = "com.zeelauncher.core_input"
}

dependencies {
    api(libs.kotlinx.coroutines.core)
}
