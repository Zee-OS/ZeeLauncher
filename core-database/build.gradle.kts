plugins {
    alias(libs.plugins.zee.android.library)
    alias(libs.plugins.zee.room)
    alias(libs.plugins.zee.hilt)
}

android {
    namespace = "com.zeelauncher.core_database"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}
