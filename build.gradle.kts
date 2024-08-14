plugins {
	id("org.springframework.boot") version "3.3.2"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("jvm") version "2.0.0"
	kotlin("plugin.spring") version "2.0.0"
	id("com.google.devtools.ksp") version "2.0.0-1.0.23"
}

group = "com.efikay"
version = "0.2.0-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}
kotlin {
	jvmToolchain(17)
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("com.ninja-squad:springmockk:4.0.2")
	testImplementation("io.kotest:kotest-assertions-core:5.9.1")
	implementation("com.github.briandilley.jsonrpc4j:jsonrpc4j:1.6")
	implementation("com.fasterxml.jackson.core:jackson-databind")
	implementation("eu.vendeli:telegram-bot:6.6.0")
	implementation("eu.vendeli:spring-ktgram-starter:6.6.0")
	ksp("eu.vendeli:ksp:6.6.0")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
