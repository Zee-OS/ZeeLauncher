plugins {
    alias(libs.plugins.zee.android.library)
}

android {
    namespace = "com.zeelauncher.core_database"
}

dependencies {
    implementation(projects.coreModel)
}
