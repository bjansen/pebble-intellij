import org.jetbrains.intellij.tasks.PublishTask

val ideaVersion: String by project
val downloadIdeaSources: String by project
val publishUsername: String by project
val publishPassword: String by project
val publishChannels: String by project
val pluginVerifier: Configuration by configurations.creating

buildscript {

    repositories {
        mavenCentral()
        maven {
            url = uri("http://dl.bintray.com/jetbrains/intellij-plugin-service")
        }
    }
}

plugins {
    id("org.jetbrains.intellij") version "0.4.22"
    id("org.sonarqube") version "3.0"
    kotlin("jvm") version "1.4.0"
}

project(":") {
    repositories {
        mavenCentral()
        maven {
            url = uri("http://dl.bintray.com/jetbrains/intellij-plugin-service")
        }
    }

    dependencies {
        implementation(project(":parser")) {
            exclude(module = "antlr4")
        }
        implementation("org.antlr", "antlr4-intellij-adaptor", "0.1")
        pluginVerifier("org.jetbrains.intellij.plugins:verifier-cli:1.241:all") {
            exclude(group = "*")
        }
    }

    apply {
        plugin("kotlin")
        plugin("org.jetbrains.intellij")
    }

    intellij {
        version = ideaVersion
        downloadSources = downloadIdeaSources.toBoolean()
        updateSinceUntilBuild = false
        setPlugins("Spring", "java-i18n", "properties"/*, "java"*/)

        tasks.withType<PublishTask> {
            username(publishUsername)
            password(publishPassword)
            channels(publishChannels)
        }
    }

    sonarqube {
        properties {
            property("sonar.projectKey", "bjansen_pebble-intellij")
        }
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType(JavaCompile::class) {
        options.encoding = "UTF-8"
    }

    tasks.register("pluginVerifier", JavaExec::class.java) {
        dependsOn("assemble")

        val pluginVerifierIdes = (properties["pluginVerifierIdes"] ?: "") as String
        val additionalIdes =
            if (pluginVerifierIdes == "") emptyArray()
            else pluginVerifierIdes.split(",").toTypedArray()

        classpath = pluginVerifier
        main = "com.jetbrains.pluginverifier.PluginVerifierMain"
        args = listOf(
            "-verification-reports-dir", "build/pluginVerifier",
            "check-plugin",
            "build/distributions/pebble-intellij.zip",
            (tasks["runIde"].property("ideaDirectory") as File?)?.absolutePath ?: "missing",
            *additionalIdes
        )
    }
}
