project.base.archivesName.set("application")

plugins {
    id(Plugins.SPRING_BOOT) version PluginsVersions.SPRING_BOOT
    id(Plugins.SPRING_DEPENDENCY_MANAGEMENT) version PluginsVersions.SPRING_DEPENDENCY_MANAGEMENT
    id(Plugins.SPRING_KOTLIN) version PluginsVersions.SPRING_KOTLIN
}

dependencies {
    // project
    implementation(project(":domain"))
    implementation(project(":usecase"))
    implementation(project(":rest"))
    implementation(project(":persistence-postgresql"))
    implementation(project(":common"))
    // kotlin
    implementation(Libs.KOTLIN_JDK8)
    implementation(Libs.KOTLIN_REFLECT)
    implementation(Libs.KOTLIN_STDLIB)
    implementation(Libs.ARROW)
    // spring
    implementation(Libs.SPRING_BOOT_STARTER_WEB)
    implementation(Libs.SPRING_BOOT_STARTER_LOGGING)
    // swagger
    implementation(Libs.SWAGGER)
    implementation(Libs.SWAGGER_UI)
    // jackson
    implementation(Libs.JACKSON_KOTLIN)
    implementation(Libs.JACKSON_DATABIND)
    implementation(Libs.JACKSON_JSR)

    // test
    testRuntimeOnly(Libs.JUNIT_ENGINE)
    testImplementation(testFixtures(project(":domain")))
    testImplementation(testFixtures(project(":usecase")))
    testImplementation(testFixtures(project(":rest")))
    testImplementation(testFixtures(project(":persistence-postgresql")))
    testImplementation(testFixtures(project(":common")))
    testImplementation(Libs.KOTEST_JUNIT)
    testImplementation(Libs.KOTEST_ARROW)
    testImplementation(Libs.JUNIT_PARAMS)
    testImplementation(Libs.JUNIT)
    testImplementation(Libs.TESTCONTAINERS_POSTGRESQL)
    testImplementation(Libs.TESTCONTAINERS_JUNIT)
    testImplementation(Libs.SPRING_BOOT_STARTER_JDBC)
    testImplementation(Libs.SPRING_BOOT_STARTER_TEST) {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

    testFixturesImplementation(testFixtures(project(":domain")))
    testFixturesImplementation(testFixtures(project(":usecase")))
    testFixturesImplementation(testFixtures(project(":rest")))
    testFixturesImplementation(testFixtures(project(":persistence-postgresql")))
    testFixturesImplementation(testFixtures(project(":common")))
    testFixturesImplementation(Libs.ARROW)
    testFixturesImplementation(Libs.LIQUIBASE)
    testFixturesImplementation(Libs.TESTCONTAINERS_POSTGRESQL)
    testFixturesImplementation(Libs.TESTCONTAINERS_JUNIT)
    testFixturesImplementation(Libs.SPRING_BOOT_STARTER_JDBC)
    testImplementation(Libs.SPRING_BOOT_STARTER_TEST) {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

tasks.build {
    dependsOn("bootJar")
}