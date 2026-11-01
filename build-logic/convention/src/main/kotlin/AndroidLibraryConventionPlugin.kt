import com.android.build.api.dsl.LibraryExtension
import com.dwiaziprasetya.zeelauncher.buildlogic.ZeeConfig
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")

            extensions.configure<LibraryExtension> {
                compileSdk = ZeeConfig.COMPILE_SDK

                defaultConfig {
                    minSdk = ZeeConfig.MIN_SDK
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                compileOptions {
                    sourceCompatibility = ZeeConfig.JAVA_VERSION
                    targetCompatibility = ZeeConfig.JAVA_VERSION
                }
            }
        }
    }
}
