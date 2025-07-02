plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

include("domain")
include("usecase")
include("rest")
include("persistence-postgresql")
include("application")
include("common")
include("exchange-rate-provider-in-memory")

rootProject.name = "Exchange"
