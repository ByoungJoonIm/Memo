plugins {
}

dependencies {
    implementation(project(":outbound"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.google.guava:guava:13.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
}
