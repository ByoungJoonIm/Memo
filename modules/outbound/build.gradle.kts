@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.kotlin.spring)
}

dependencies {
    implementation(project(":common"))

    implementation(libs.jackson.annotations)
    api(libs.spring.boot.starter.data.jpa)
    api(libs.spring.boot.starter.webflux)
    api(libs.spring.boot.starter.web)
    implementation(libs.guava)

    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.h2)
}
