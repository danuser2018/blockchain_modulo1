val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val commonsCodecVersion: String by project
val kotestVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.6.21"
}

group = "blockchain"
version = "0.0.1"
application {
    mainClass.set("blockchain.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

tasks.register<Test>("integrationTest") {
    dependsOn(tasks.named("test"))
    exclude("**/*Test.*")
}

tasks.named<Test>("test") {
    exclude("**/*IT.*")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.named("check").configure {
    dependsOn(tasks.named("integrationTest"))
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

dependencies {
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-jackson-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("commons-codec:commons-codec:$commonsCodecVersion")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
}