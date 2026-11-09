plugins {
    alias(libs.plugins.zee.android.library)
    alias(libs.plugins.zee.hilt)
}

android {
    namespace = "com.zeelauncher.core_common"
}

dependencies {
    api(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
}
