plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    api(libs.kotlinx.serialization.json)
    api(libs.okhttp3.okhttp)
    api(libs.okhttp3.logging.interceptor)
    implementation(libs.retrofit2.retrofit)
    implementation(libs.retrofit2.converter.kotlinx.serialization)
}
