import org.jetbrains.intellij.tasks.PublishPluginTask
import org.jetbrains.intellij.tasks.RunPluginVerifierTask
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
    id("org.jetbrains.intellij") version "1.12.0"
    id("org.sonarqube") version "3.5.0.2730"
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

        if (kotlinVersion > "1.4") {
            implementation("org.jetbrains.kotlin", "kotlin-reflect", kotlinVersion)
        }
    }

    apply {
        plugin("kotlin")
        plugin("org.jetbrains.intellij")
    }

    intellij {
        version.set(ideaVersion)
        downloadSources.set(downloadIdeaSources.toBoolean())
        updateSinceUntilBuild.set(false)
        plugins.set(listOf("Spring", "java-i18n", "properties", "java", intellilangName))

        tasks.withType<PublishPluginTask> {
            token.set(publishToken)
            channels.set(publishChannels.split(','))
        }

        tasks.withType<RunPluginVerifierTask> {
            ideVersions.set(listOf("IU-2019.2.3", "IU-2020.3.2"))
        }
    }

    tasks.jacocoTestReport {
        reports {
            xml.isEnabled = true
            csv.isEnabled = false
            html.isEnabled = false
        }
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType(JavaCompile::class) {
        options.encoding = "UTF-8"
    }
}
