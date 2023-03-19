plugins {
}

dependencies {
    implementation(project(":outbound"))
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
