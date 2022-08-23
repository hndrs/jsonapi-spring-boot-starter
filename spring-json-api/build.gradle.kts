dependencies {
    api(group = "org.springframework.boot", name = "spring-boot-starter-web")

    testImplementation("io.mockk:mockk:1.12.6")
}

publishingInfo {
    description = "SpringBoot json api response classes and advices"
}
publishing {
    val sourcesJarSubProject by tasks.creating(Jar::class) {
        dependsOn("classes")
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    publications {
        println("Pub ${project.name}")
        create<MavenPublication>(project.name) {
            from(components["java"])
            artifact(sourcesJarSubProject)

            groupId = rootProject.group as? String
            artifactId = project.name
            version = "${rootProject.version}${project.findProperty("version.appendix") ?: ""}"
            pom {

            }
        }
        val signingKey: String? = System.getenv("SIGNING_KEY")
        val signingPassword: String? = System.getenv("SIGNING_PASSWORD")
        if (signingKey != null && signingPassword != null) {
            signing {
                useInMemoryPgpKeys(groovy.json.StringEscapeUtils.unescapeJava(signingKey), signingPassword)
                sign(publications[project.name])
            }
        }
    }
}
