rootProject.name = "hndrs-jsonapi-spring-boot-starter"

include("spring-json-api")
project(":spring-json-api").projectDir = File("spring-json-api")

include("jsonapi-spring-boot-starter")
project(":jsonapi-spring-boot-starter").projectDir = File("spring-json-api-starter")

include("sample")
project(":sample").projectDir = File("sample")

pluginManagement {
    val kotlinVersion: String by settings
    val springDependencyManagement: String by settings
    plugins {
        id("io.spring.dependency-management").version(springDependencyManagement)
        kotlin("jvm").version(kotlinVersion)
        kotlin("plugin.spring").version(kotlinVersion)
        kotlin("kapt").version(kotlinVersion)
        id("maven-publish")
        id("idea")
    }
    repositories {
    }
}
