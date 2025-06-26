plugins {
    id("java-library")
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}
java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
    }
}
