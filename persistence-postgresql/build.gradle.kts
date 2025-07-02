project.base.archivesName.set("persistence-postgresql")

plugins {
    id(Plugins.SPRING_KOTLIN) version PluginsVersions.SPRING_KOTLIN
}

dependencies {
    // project
    implementation(project(":domain"))
    implementation(project(":usecase"))
    implementation(project(":common"))
    // kotlin
    implementation(Libs.KOTLIN_JDK8)
    implementation(Libs.KOTLIN_REFLECT)
    implementation(Libs.KOTLIN_STDLIB)
    implementation(Libs.ARROW)
    // spring
    implementation(Libs.SPRING_BOOT_STARTER_JDBC)
    // jackson
    implementation(Libs.JACKSON_KOTLIN)
    implementation(Libs.JACKSON_DATABIND)
    implementation(Libs.JACKSON_JSR)
    // database
    implementation(Libs.POSTGRESQL)
    implementation(Libs.LIQUIBASE)
}
