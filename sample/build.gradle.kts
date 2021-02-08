plugins {
    id("org.springframework.boot").version("2.4.2")
}
repositories {
    mavenCentral()
}

dependencies {
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-web")
    implementation(project(":spring-json-api-starter"))
}
