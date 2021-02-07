rootProject.name = "jsonapi-spring-boot-starter"

include("spring-json-api")
project(":spring-json-api").projectDir = File("spring-json-api")

include("spring-json-api-starter")
project(":spring-json-api-starter").projectDir = File("spring-json-api-starter")
