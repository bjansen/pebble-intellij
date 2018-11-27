repositories {
    mavenCentral()
}

plugins {
    java
    antlr
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {
    antlr("org.antlr:antlr4:4.7.1")
    compile("org.antlr:antlr4-runtime:4.7.1")
    testCompile("junit:junit:4.+")
}

tasks.generateGrammarSource {
    arguments = arguments + listOf("-visitor")
}
