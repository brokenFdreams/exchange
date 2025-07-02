project.base.archivesName.set("rest")

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
    implementation(Libs.SPRING_BOOT_STARTER_WEB)

    // jackson
    implementation(Libs.JACKSON_KOTLIN)

    // swagger
    implementation(Libs.SWAGGER)
}