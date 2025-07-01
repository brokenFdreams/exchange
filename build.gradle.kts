import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val parentProjectDir = projectDir

plugins {
    id(Plugins.KOTLIN) version PluginsVersions.KOTLIN apply false
    id(Plugins.UPDATE_DEPENDENCIES) version PluginsVersions.UPDATE_DEPENDENCIES
}

subprojects {
    configurations.all {
        resolutionStrategy {
            eachDependency {
                requested.version?.contains("snapshot", true)?.let {
                    if (it) {
                        throw GradleException("Snapshot found: ${requested.name} ${requested.version}")
                    }
                }
            }
        }
    }

    apply {
        plugin("java")
        plugin(Plugins.KOTLIN)
        plugin("jacoco")
        plugin(Plugins.JAVA_TEST_FIXTURES)
        plugin(Plugins.UPDATE_DEPENDENCIES)
//        plugin(Plugins.OWASP_DEPENDENCIES)
    }

    repositories {
        mavenCentral()
        mavenLocal()
    }

    tasks {
        val check = named<DefaultTask>("check")
        val jacocoTestReport = named<JacocoReport>("jacocoTestReport")
        val jacocoTestCoverageVerification = named<JacocoCoverageVerification>("jacocoTestCoverageVerification")
        val dependencyUpdate =
            named<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask>("dependencyUpdates")

        dependencyUpdate {
            revision = "release"
            outputFormatter = "txt"
            checkForGradleUpdate = true
            outputDir = "${layout.buildDirectory}/reports/dependencies"
            reportfileName = "updates"
        }

        dependencyUpdate.configure {

            fun isNonStable(version: String): Boolean {
                val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
                val regex = "^[0-9,.v-]+(-r)?$".toRegex()
                val isStable = stableKeyword || regex.matches(version)
                return isStable.not()
            }

            rejectVersionIf {
                isNonStable(candidate.version) && !isNonStable(currentVersion)
            }
        }

        check {
            finalizedBy(jacocoTestReport)
            finalizedBy(dependencyUpdate)
        }

        jacocoTestReport {
            dependsOn(check)
            finalizedBy(jacocoTestCoverageVerification)
        }

        jacocoTestCoverageVerification {
            dependsOn(jacocoTestReport)

            violationRules {

                rule {
                    excludes = listOf("application", "mock-server")
                    limit {
                        minimum = BigDecimal("0.5")
                    }
                }
            }
        }

        val failOnWarning = project.properties["allWarningsAsErrors"] != null && project
            .properties["allWarningsAsErrors"] == "true"

        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            compilerOptions {
                jvmTarget = JvmTarget.JVM_21
                allWarningsAsErrors = failOnWarning
                freeCompilerArgs = listOf("-Xjvm-default=all-compatibility")
            }
        }

        withType<JavaCompile> {
            options.compilerArgs.add("-Xlint:all")
            targetCompatibility = JavaVersion.VERSION_21.toString()
            sourceCompatibility = JavaVersion.VERSION_21.toString()

        }

        withType<Test> {
            useJUnitPlatform()

            testLogging {
                events(
                    org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
                    org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
                    org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
                )
                showStandardStreams = true
                exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
            }

            systemProperties["pact.rootDir"] = "${layout.buildDirectory}/pacts"
        }
    }
}