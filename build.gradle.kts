import org.jetbrains.intellij.tasks.PublishPluginTask
import kotlin.collections.listOf

val ideaVersion: String by project
val kotlinVersion: String by project
val intellilangName: String by project
val downloadIdeaSources: String by project
val publishToken: String by project
val publishChannels: String by project

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    id("org.jetbrains.intellij") version "1.17.0"
    id("org.sonarqube") version "4.3.0.3225"
    kotlin("jvm")
    jacoco
}

project(":") {
    repositories {
        mavenCentral()
    }

    dependencies {
        implementation(project(":parser")) {
            exclude(module = "antlr4")
        }
        implementation("org.antlr", "antlr4-intellij-adaptor", "0.1")
        implementation("org.jetbrains.kotlin", "kotlin-reflect", kotlinVersion)
    }

    apply {
        plugin("kotlin")
        plugin("org.jetbrains.intellij")
    }

    intellij {
        version.set(ideaVersion)
        downloadSources.set(downloadIdeaSources.toBoolean())
        updateSinceUntilBuild.set(false)
        plugins.set(listOf("Spring", "java-i18n", "properties", "java", "org.intellij.intelliLang"))

        tasks.withType<PublishPluginTask> {
            token.set(publishToken)
            channels.set(publishChannels.split(','))
        }
    }

    tasks.jacocoTestReport {
        reports {
            xml.required.set(true)
            csv.required.set(false)
            html.required.set(false)
        }
    }

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType(JavaCompile::class) {
        options.encoding = "UTF-8"
    }
}
