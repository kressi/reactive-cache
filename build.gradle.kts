plugins {
  `java-library`
  id("com.diffplug.spotless") version "6.18.0"
  id("io.freefair.lombok") version "8.0.1"
}

java { toolchain { languageVersion.set(JavaLanguageVersion.of(17)) } }

repositories { mavenCentral() }

dependencies {
  api("io.projectreactor:reactor-core:3.5.6")
  api("io.projectreactor.addons:reactor-extra:3.5.1")
  api("com.github.ben-manes.caffeine:caffeine:3.1.6")
  api("org.springframework:spring-context:6.0.9")
  implementation("org.slf4j:slf4j-api:2.0.7")
  testImplementation("io.projectreactor:reactor-test:3.5.6")
  testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
  testRuntimeOnly("org.slf4j:slf4j-simple:2.0.7")
}

tasks.compileJava { options.compilerArgs.addAll(listOf("-Werror", "-Xlint:all")) }

tasks.named<Test>("test") { useJUnitPlatform() }

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
  format("misc") {
    target("*.md", ".gitignore", ".gitattributes", "*.properties")
    trimTrailingWhitespace()
    indentWithSpaces(2)
    endWithNewline()
  }
  java {
    toggleOffOn()
    importOrder()
    removeUnusedImports()
    cleanthat()
    googleJavaFormat()
    formatAnnotations()
    trimTrailingWhitespace()
    endWithNewline()
  }
  kotlinGradle { ktfmt() }
}
