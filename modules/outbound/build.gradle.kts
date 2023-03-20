plugins {
    kotlin("plugin.jpa") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
}

dependencies {
    implementation(project(":common"))

    implementation("com.fasterxml.jackson.core:jackson-annotations:2.14.2")
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.google.guava:guava:13.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.h2database:h2:2.1.214")
}
