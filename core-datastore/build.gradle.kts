plugins {
    alias(libs.plugins.zee.android.library)
    alias(libs.plugins.zee.hilt)
}

android {
    namespace = "com.zeelauncher.core_datastore"
}

dependencies {
    implementation(projects.coreModel)
    implementation(libs.androidx.datastore.preferences)
}
