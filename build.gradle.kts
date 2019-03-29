import org.jetbrains.intellij.tasks.PublishTask

val ideaVersion: String by project
val downloadIdeaSources: String by project
val publishUsername: String by project
val publishPassword: String by project
val publishChannels: String by project

buildscript {

    repositories {
        mavenCentral()
        maven {
            url = uri("http://dl.bintray.com/jetbrains/intellij-plugin-service")
        }
    }
}

plugins {
    id("org.jetbrains.intellij") version "0.4.7"
    kotlin("jvm") version "1.3.21"
}

project(":") {
    repositories {
        mavenCentral()
    }

    dependencies {
        compile(project(":parser")) {
            exclude(module="antlr4")
        }
        compile("org.antlr", "antlr4-intellij-adaptor", "0.1")
    }

    apply {
        plugin("kotlin")
        plugin("org.jetbrains.intellij")
    }

    intellij {
        version = ideaVersion
        downloadSources = downloadIdeaSources.toBoolean()
        updateSinceUntilBuild = false
        instrumentCode = false
        setPlugins("Spring", "java-i18n", "properties")

        tasks.withType<PublishTask> {
            username(publishUsername)
            password(publishPassword)
            channels(publishChannels)
        }
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_7
        targetCompatibility = JavaVersion.VERSION_1_7
    }

    tasks.withType(JavaCompile::class) {
        options.encoding = "UTF-8"
    }

    sourceSets {
        getByName("main").apply {
            java.srcDirs("antlr-adaptor/src")
        }
    }
}
