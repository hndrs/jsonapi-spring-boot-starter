import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

object Versions {
    const val KOTLIN_VERSION = "1.4.30"
}

buildscript {
    repositories {
        mavenCentral()
        maven(url = "https://repo.spring.io/plugins-release")
    }
    dependencies {
        classpath("io.spring.gradle:propdeps-plugin:0.0.9.RELEASE")
    }
}

plugins {
    id("org.sonarqube").version("3.0")
    `maven-publish`
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    kotlin("jvm").version("1.4.30")
    kotlin("plugin.spring").version("1.4.30")
    kotlin("kapt").version("1.4.30")
    id("java")
    id("maven-publish")
    id("idea")
}

group = "com.elvah.auth"
version = rootProject.file("version.txt").readText().trim()
java.sourceCompatibility = JavaVersion.VERSION_15
java.targetCompatibility = JavaVersion.VERSION_15

repositories {
    mavenCentral()
}

subprojects {

    apply(plugin = "kotlin")
    apply(plugin = "java-library")
    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "maven-publish")
    apply(plugin = "jacoco")
    apply(plugin = "propdeps")
    apply(plugin = "propdeps-idea")



    dependencyManagement {
        resolutionStrategy {
            cacheChangingModulesFor(0, "seconds")
        }
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:2.4.2") {
                bomProperty("kotlin.version", Versions.KOTLIN_VERSION)
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
        testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")

    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "15"
        }
    }

    val sourcesJarSubProject by tasks.creating(Jar::class) {
        dependsOn("classes")
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    if (project.name != "sample") {
        publishing {
            repositories {

            }
            publications {
                create<MavenPublication>(project.name) {
                    from(components["java"])
                    artifact(sourcesJarSubProject)

                    groupId = rootProject.group as? String
                    artifactId = project.name
                    version = "${rootProject.version}${project.findProperty("version.appendix") ?: ""}"
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

