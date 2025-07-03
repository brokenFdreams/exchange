project.base.archivesName.set("application")

plugins {
    id(Plugins.SPRING_BOOT) version PluginsVersions.SPRING_BOOT
    id(Plugins.SPRING_DEPENDENCY_MANAGEMENT) version PluginsVersions.SPRING_DEPENDENCY_MANAGEMENT
    id(Plugins.SPRING_KOTLIN) version PluginsVersions.SPRING_KOTLIN
    id(Plugins.JIB) version PluginsVersions.JIB
}

dependencies {
    // project
    implementation(project(":domain"))
    implementation(project(":usecase"))
    implementation(project(":rest"))
    implementation(project(":persistence-postgresql"))
    implementation(project(":exchange-rate-provider-in-memory"))
    implementation(project(":common"))
    // kotlin
    implementation(Libs.KOTLIN_JDK8)
    implementation(Libs.KOTLIN_REFLECT)
    implementation(Libs.KOTLIN_STDLIB)
    implementation(Libs.ARROW)
    // spring
    implementation(Libs.SPRING_BOOT_STARTER_WEB)
    implementation(Libs.SPRING_BOOT_STARTER_LOGGING)
    implementation(Libs.SPRING_BOOT_STARTER_JDBC)
    // database
    implementation(Libs.POSTGRESQL)
    implementation(Libs.LIQUIBASE)
    // swagger
    implementation(Libs.SWAGGER)
    // jackson
    implementation(Libs.JACKSON_KOTLIN)
    implementation(Libs.JACKSON_DATABIND)
    implementation(Libs.JACKSON_JSR)
}

tasks.build {
    dependsOn("bootJar")
}

jib {
    from {
        image = "eclipse-temurin:21-jre-alpine"
    }
    to {
        image = "exchange"
        tags = setOf("latest")
    }
    container {
        mainClass = "lu.exchange.ExchangeApplicationKt"
        ports = listOf("8080")
    }
}
