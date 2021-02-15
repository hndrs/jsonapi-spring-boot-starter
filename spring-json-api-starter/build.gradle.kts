dependencies {
    api(project(":spring-json-api"))
    api(group = "org.springframework.boot", name = "spring-boot-autoconfigure")

    testImplementation(group = "org.springframework.boot", name = "spring-boot-starter-test")
    testImplementation(group = "org.springframework.boot", name = "spring-boot-starter-web")
    testImplementation("io.mockk:mockk:1.10.6")
}
