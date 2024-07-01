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
include(":core:api")
include(":core:data")
include(":core:database")
include(":core:navigation")
include(":core:uikit")
include(":features:films:ui")
include(":features:films:ui_logic")
include(":features:persons:ui")
include(":features:persons:ui_logic")
include(":features:planet:ui")
include(":features:planet:ui_logic")
