project.base.archivesName.set("common")

dependencies {
    // kotlin
    implementation(Libs.KOTLIN_JDK8)
    implementation(Libs.KOTLIN_REFLECT)
    implementation(Libs.KOTLIN_STDLIB)
    implementation(Libs.ARROW)

    // test
    testImplementation(Libs.KOTEST_JUNIT)
    testImplementation(Libs.KOTEST_ARROW)
    testImplementation(Libs.JUNIT_ENGINE)
    testImplementation(Libs.JUNIT_PARAMS)

    testFixturesImplementation(Libs.ARROW)
}
