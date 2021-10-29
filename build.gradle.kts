import io.hndrs.gradle.plugin.Developer
import io.hndrs.gradle.plugin.License
import io.hndrs.gradle.plugin.Organization
import io.hndrs.gradle.plugin.Scm
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }
}

val springBootDependencies: String by extra
val kotlinVersion: String by extra

plugins {
    id("org.sonarqube").version("3.3")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("kapt")
    id("java")
    id("maven-publish")
    id("idea")
    id("signing")
    id("io.hndrs.publishing-info").version("2.0.0")
}

group = "io.hndrs"
version = rootProject.file("version.txt").readText().trim()
java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

sonarqube {
    properties {
        property("sonar.projectKey", "hndrs_jsonapi-spring-boot-starter")
        property("sonar.organization", "hndrs")
        property("sonar.host.url", "https://sonarcloud.io")
        property(
            "sonar.exclusions", "**/sample/**, **/response/Meta.kt," +
                    " **/response/Response.kt, **/response/ErrorResponse.kt"
        )
    }
}

subprojects {

    apply(plugin = "kotlin")
    apply(plugin = "java-library")
    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "maven-publish")
    apply(plugin = "jacoco")
    apply(plugin = "signing")

    publishingInfo {
        name = project.name
        url = "https://github.com/hndrs/jsonapi-spring-boot-starter"
        license = License(
            "https://github.com/hndrs/jsonapi-spring-boot-starter/blob/main/LICENSE",
            "MIT License"
        )
        developers = listOf(
            Developer("marvinschramm", "Marvin Schramm", "marvin.schramm@gmail.com")
        )
        organization = Organization("hndrs", "https://oss.hndrs.io")
        scm = Scm(
            "scm:git:git://github.com/hndrs/jsonapi-spring-boot-starter",
            "https://github.com/hndrs/jsonapi-spring-boot-starter"
        )
    }

    java {
        withJavadocJar()
    }

    dependencyManagement {
        resolutionStrategy {
            cacheChangingModulesFor(0, "seconds")
        }
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootDependencies") {
                bomProperty("kotlin.version", kotlinVersion)
            }
        }
    }

    repositories {
        mavenCentral()
    }

    tasks.withType<Wrapper> {
        gradleVersion = "6.8.2"
        // anything else
    }

    dependencies {
        api("org.jetbrains.kotlin:kotlin-reflect")
        api("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        testImplementation(platform("org.junit:junit-bom:5.7.0"))
        testImplementation("org.junit.jupiter:junit-jupiter")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    if (project.name != "sample") {

        publishing {
            repositories {
                maven {
                    name = "release"
                    url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
                    credentials {
                        username = System.getenv("SONATYPE_USER")
                        password = System.getenv("SONATYPE_PASSWORD")
                    }
                }
            }
        }
    }

    configure<JacocoPluginExtension> {
        toolVersion = "0.8.6"
    }

    tasks.withType<JacocoReport> {
        reports {
            xml.apply {
                isEnabled = true
            }

        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

