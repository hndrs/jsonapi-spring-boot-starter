dependencies {
    api(project(":spring-json-api"))
    api(group = "org.springframework.boot", name = "spring-boot-autoconfigure")

    testImplementation(group = "org.springframework.boot", name = "spring-boot-starter-test")
    testImplementation(group = "org.springframework.boot", name = "spring-boot-starter-web")
}

publishingInfo {
    description = "SpringBoot json api response starter"
}
publishing{
    val sourcesJarSubProject by tasks.creating(Jar::class) {
        dependsOn("classes")
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    publications {
        create<MavenPublication>(project.name) {
            from(components["java"])
            artifact(sourcesJarSubProject)

            groupId = rootProject.group as? String
            artifactId = project.name
            version = "${rootProject.version}${project.findProperty("version.appendix") ?: ""}"
            pom {

            }
        }
    }
}
