pluginManagement {
    repositories {
        google()
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
rootProject.name = "AllInOne"
include(":app")
include(":resource")
include(":base")
include(":feature:auth")
include(":feature:home")
include(":feature:daily")
include(":feature:mypage")
include(":firebase")
