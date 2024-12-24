import org.jetbrains.intellij.platform.gradle.TestFrameworkType

val ideaVersion: String by project
val kotlinVersion: String by project
val intellilangName: String by project
val downloadIdeaSources: String by project
val publishToken: String by project
val publishChannels: String by project
val pluginVersion: String by project
val sinceBuildVersion: String by project
val untilBuildVersion: String by project

version=pluginVersion

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    id("org.jetbrains.intellij.platform") version "2.5.0"
    id("org.sonarqube") version "4.3.0.3225"
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
        implementation("org.antlr", "antlr4-intellij-adaptor", "0.1")
        implementation("org.jetbrains.kotlin", "kotlin-reflect", kotlinVersion)

        intellijPlatform {
            create(ideaVersion)

            bundledPlugins("com.intellij.spring", "com.intellij.spring.boot", "com.intellij.java", "org.intellij.intelliLang")
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
