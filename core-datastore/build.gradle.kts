plugins {
    alias(libs.plugins.zee.android.library)
}

android {
    namespace = "com.zeelauncher.core_datastore"
}

dependencies {
    implementation(projects.coreModel)
}
