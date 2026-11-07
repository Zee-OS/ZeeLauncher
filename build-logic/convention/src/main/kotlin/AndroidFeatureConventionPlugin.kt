import com.dwiaziprasetya.zeelauncher.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("zee.android.library")
            pluginManager.apply("zee.android.compose")
            pluginManager.apply("zee.hilt")
            pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

            dependencies {
                add("implementation", project(":core-common"))
                add("implementation", project(":core-data"))
                add("implementation", project(":core-designsystem"))
                add("implementation", project(":core-input"))
                add("implementation", project(":core-navigation"))
                add("implementation", libs.findLibrary("androidx-lifecycle-viewmodel-compose").get())
                add("implementation", libs.findLibrary("androidx-lifecycle-runtime-compose").get())
                add("implementation", libs.findLibrary("androidx-hilt-navigation-compose").get())
                add("implementation", libs.findLibrary("coil-compose").get())
            }
        }
    }
}
