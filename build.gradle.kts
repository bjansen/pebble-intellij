import org.jetbrains.intellij.tasks.PublishTask
import org.jetbrains.intellij.tasks.RunPluginVerifierTask
import kotlin.collections.listOf

val ideaVersion: String by project
val downloadIdeaSources: String by project
val publishUsername: String by project
val publishPassword: String by project
val publishChannels: String by project

buildscript {

    repositories {
        mavenCentral()
    }
}

plugins {
    id("org.jetbrains.intellij") version "1.8.1"
    id("org.sonarqube") version "3.3"
    kotlin("jvm") version "1.3.72"
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
    }

    apply {
        plugin("kotlin")
        plugin("org.jetbrains.intellij")
    }

    intellij {
        version = ideaVersion
        downloadSources = downloadIdeaSources.toBoolean()
        updateSinceUntilBuild = false
        setPlugins("Spring", "java-i18n", "properties", "java", "IntelliLang")

        tasks.withType<PublishTask> {
            username(publishUsername)
            password(publishPassword)
            channels(publishChannels)
        }

        tasks.withType<RunPluginVerifierTask> {
            setIdeVersions(listOf("IU-2019.2.3", "IU-2020.3.2"))
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
