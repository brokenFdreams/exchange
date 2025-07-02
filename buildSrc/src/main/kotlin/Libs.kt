object LibsVersions {
    const val SPRING_BOOT = "3.5.3"
    const val ARROW = "2.1.2"
    const val JACKSON = "2.19.1"
    const val SWAGGER = "2.8.9"
    const val POSTGRESQL = "42.7.5"
    const val LIQUIBASE = "4.32.0"
    const val KOTEST = "5.9.1"
    const val KOTEST_ARROW = "2.0.0"
    const val JUNIT = "5.13.2"
    const val TESTCONTAINERS = "1.21.3"
}

object Libs {
    // Kotlin
    const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:${Global.KOTLIN_VERSION}"
    const val KOTLIN_JDK8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Global.KOTLIN_VERSION}"
    const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect:${Global.KOTLIN_VERSION}"
    const val ARROW = "io.arrow-kt:arrow-core:${LibsVersions.ARROW}"

    // Jackson
    const val JACKSON_KOTLIN = "com.fasterxml.jackson.module:jackson-module-kotlin:${LibsVersions.JACKSON}"
    const val JACKSON_DATABIND = "com.fasterxml.jackson.core:jackson-databind:${LibsVersions.JACKSON}"
    const val JACKSON_JSR = "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${LibsVersions.JACKSON}"

    // Spring
    const val SPRING_BOOT_STARTER_WEB = "org.springframework.boot:spring-boot-starter-web:${LibsVersions.SPRING_BOOT}"
    const val SPRING_BOOT_STARTER_JDBC = "org.springframework.boot:spring-boot-starter-data-jdbc:${LibsVersions.SPRING_BOOT}"
    const val SPRING_BOOT_STARTER_LOGGING =
        "org.springframework.boot:spring-boot-starter-logging:${LibsVersions.SPRING_BOOT}"
    const val SPRING_BOOT_STARTER_TEST = "org.springframework.boot:spring-boot-starter-test:${LibsVersions.SPRING_BOOT}"

    // Database
    const val POSTGRESQL = "org.postgresql:postgresql:${LibsVersions.POSTGRESQL}"
    const val LIQUIBASE = "org.liquibase:liquibase-core:${LibsVersions.LIQUIBASE}"

    // Swagger
    const val SWAGGER = "org.springdoc:springdoc-openapi-starter-webmvc-ui:${LibsVersions.SWAGGER}"

    //Test
    const val JUNIT_PARAMS = "org.junit.jupiter:junit-jupiter-params:${LibsVersions.JUNIT}"
    const val JUNIT = "org.junit.jupiter:junit-jupiter:${LibsVersions.JUNIT}"
    const val JUNIT_ENGINE = "org.junit.jupiter:junit-jupiter-engine:${LibsVersions.JUNIT}"
    const val KOTEST_JUNIT = "io.kotest:kotest-runner-junit5:${LibsVersions.KOTEST}"
    const val KOTEST_ARROW = "io.kotest.extensions:kotest-assertions-arrow-jvm:${LibsVersions.KOTEST_ARROW}"
    const val TESTCONTAINERS_POSTGRESQL = "org.testcontainers:postgresql:${LibsVersions.TESTCONTAINERS}"
    const val TESTCONTAINERS_JUNIT = "org.testcontainers:junit-jupiter:${LibsVersions.TESTCONTAINERS}"
}