project.base.archivesName.set("usecase")

dependencies {
    //project
    implementation(project(":common"))
    implementation(project(":domain"))

    // kotlin
    implementation(Libs.KOTLIN_JDK8)
    implementation(Libs.KOTLIN_REFLECT)
    implementation(Libs.KOTLIN_STDLIB)
    implementation(Libs.ARROW)
}