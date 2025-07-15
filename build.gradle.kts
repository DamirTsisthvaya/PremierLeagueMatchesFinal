plugins {
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
    alias(libs.plugins.android.application) apply false
    
    alias(libs.plugins.kotlin.android) apply false

}

// Файл: build.gradle.kts (Project level)
buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {

        classpath("com.android.tools.build:gradle:8.11.1") // Или новее (проверьте последнюю версию)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.0") // Kotlin 2.0+
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.56.2")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}