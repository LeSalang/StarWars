pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Star Wars"
include(":app")
include(":uikit")
include(":api")
include(":data")
include(":features:films:ui_logic")
include(":database")
include(":features:films:ui")
include(":features:persons:ui_logic")
include(":features:persons:ui")
