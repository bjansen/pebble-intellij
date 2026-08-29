rootProject.name = "pebble-intellij"
include("parser")

pluginManagement {
    val kotlinVersion: String = providers.gradleProperty("kotlinVersion").get()

    plugins {
        kotlin("jvm") version kotlinVersion
    }
}
