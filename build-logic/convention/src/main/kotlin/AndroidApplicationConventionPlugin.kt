import com.android.build.api.dsl.ApplicationExtension
import com.dwiaziprasetya.zeelauncher.buildlogic.ZeeConfig
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")

            extensions.configure<ApplicationExtension> {
                compileSdk = ZeeConfig.COMPILE_SDK

                defaultConfig {
                    minSdk = ZeeConfig.MIN_SDK
                    targetSdk = ZeeConfig.TARGET_SDK
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                buildTypes {
                    getByName("debug") {
                        applicationIdSuffix = ".debug"
                        versionNameSuffix = "-debug"
                    }
                    getByName("release") {
                        isMinifyEnabled = true
                        isShrinkResources = true
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro",
                        )
                    }
                }

                compileOptions {
                    sourceCompatibility = ZeeConfig.JAVA_VERSION
                    targetCompatibility = ZeeConfig.JAVA_VERSION
                }
            }
        }
    }
}
