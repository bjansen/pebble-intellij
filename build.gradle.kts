import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import java.util.regex.Pattern

val ideaVersion: String = project.property("ideaVersion").toString()
val kotlinVersion: String = project.property("kotlinVersion").toString()
val intellilangName = project.findProperty("intellilangName")
val downloadIdeaSources: String = project.property("downloadIdeaSources").toString()
val publishToken: String = project.property("publishToken").toString()
val publishChannels: String = project.property("publishChannels").toString()
val pluginVersion: String = project.property("pluginVersion").toString()
val sinceBuildVersion: String = project.property("sinceBuildVersion").toString()
val untilBuildVersion: String = project.property("untilBuildVersion").toString()
val (platformType: String, platformVersion: String) = ideaVersion.split(Pattern.compile("-"), 2)

version = pluginVersion

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    id("org.jetbrains.intellij.platform") version "2.18.1"
    id("org.sonarqube") version "7.4.0.8496"
    kotlin("jvm")
    jacoco
}

project(":") {
    repositories {
        mavenCentral()

        intellijPlatform {
            defaultRepositories()
        }
    }

    dependencies {
        implementation(project(":parser")) {
            exclude(module = "antlr4")
        }
        implementation("org.antlr:antlr4-intellij-adaptor:0.1")
        implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

        intellijPlatform {
            create(platformType, platformVersion)

            bundledPlugins(
                "com.intellij.spring",
                "com.intellij.spring.boot",
                "com.intellij.java",
                "org.intellij.intelliLang"
            )
            testFramework(TestFrameworkType.Platform)
            testFramework(TestFrameworkType.Plugin.Java)
        }

        testImplementation("junit:junit:4.13.2")
        testImplementation("org.opentest4j:opentest4j:1.3.0")
    }

    apply {
        plugin("kotlin")
        plugin("org.jetbrains.intellij.platform")
    }

    tasks.buildSearchableOptions {
        enabled = false
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
