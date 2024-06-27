import io.gitlab.arturbosch.detekt.extensions.DetektExtension

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.detekt) apply true
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
