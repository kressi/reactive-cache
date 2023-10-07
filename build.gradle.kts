plugins {
  `java-library`
  id("com.diffplug.spotless") version "6.22.0"
  id("io.freefair.lombok") version "8.3"
}

java { toolchain { languageVersion.set(JavaLanguageVersion.of(21)) } }

repositories { mavenCentral() }

dependencies {
  api("io.projectreactor:reactor-core:3.5.10")
  api("io.projectreactor.addons:reactor-extra:3.5.1")
  api("com.github.ben-manes.caffeine:caffeine:3.1.8")
  api("org.springframework:spring-context:6.0.12")
  implementation("org.slf4j:slf4j-api:2.0.9")
  testImplementation("io.projectreactor:reactor-test:3.5.10")
  testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
  testRuntimeOnly("org.slf4j:slf4j-simple:2.0.9")
}

tasks.compileJava { options.compilerArgs.addAll(listOf("-Werror", "-Xlint:all")) }

lombok { version = "1.18.30" }

tasks.named<Test>("test") { useJUnitPlatform() }

spotless {
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
