dependencies {
    implementation(project(":outbound"))
    implementation(project(":common"))
    implementation(libs.spring.boot.starter.web)
    implementation(libs.guava)

    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.mockito.kotlin)
}
