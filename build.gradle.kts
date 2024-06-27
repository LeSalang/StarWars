import io.gitlab.arturbosch.detekt.extensions.DetektExtension

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.androidx.room) apply false
    alias(libs.plugins.detekt) apply true
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.kapt) apply false
    alias(libs.plugins.ksp) apply false
}

allprojects.onEach {
    it.afterEvaluate {
        with(project.plugins) {
            if (hasPlugin(libs.plugins.jetbrains.kotlin.android.get().pluginId)) {
                apply(libs.plugins.detekt.get().pluginId)
                extensions.configure<DetektExtension> {
                    config.setFrom(rootProject.files("detekt-config.yml"))
                }
                dependencies.add(
                    "detektPlugins",
                    libs.detekt.formatting.get()
                        .toString()
                )
            }
        }
    }
}
