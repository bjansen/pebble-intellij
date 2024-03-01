repositories {
    mavenCentral()
}

plugins {
    java
    antlr
}

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {
    antlr("org.antlr:antlr4:4.13.1")
    implementation("org.antlr:antlr4-runtime:4.13.1")
    testImplementation("junit:junit:4.+")
}

tasks.generateGrammarSource {
    arguments = arguments + listOf("-visitor", "-package", "com.github.bjansen.pebble.parser", "-Xexact-output-dir")

    doLast {
        val parserPackagePath = "${outputDirectory.canonicalPath}/com/github/bjansen/pebble/parser"

        file(parserPackagePath).mkdirs()

        copy {
            from(outputDirectory.canonicalPath)
            into(parserPackagePath)
            include("Pebble*")
        }
        delete(fileTree(outputDirectory.canonicalPath) {
            include("Pebble*")
        })
    }
}
