plugins {
  `java-library`
  id("com.diffplug.spotless") version "6.18.0"
}

java { toolchain { languageVersion.set(JavaLanguageVersion.of(17)) } }

repositories { mavenCentral() }

dependencies {
  implementation("io.projectreactor:reactor-core:3.5.6")
  implementation("com.github.ben-manes.caffeine:caffeine:3.1.6")

  testImplementation("io.projectreactor:reactor-test:3.5.6")
  testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
}

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
