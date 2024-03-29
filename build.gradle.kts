import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {

    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.noarg") version "1.8.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.22"
    kotlin("kapt") version "1.8.22"

}

noArg {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

group = "org.team.b4"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    //OAUTH
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    //MAIL
    implementation("org.springframework.boot:spring-boot-starter-mail")
    //Valid
    implementation("org.springframework.boot:spring-boot-starter-validation")
    //JAVA
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.4")
    //AWS
    implementation("org.springframework.cloud:spring-cloud-starter-aws:2.0.1.RELEASE")
    //API
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.0")
    //xml형변환
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.0")
    //WEB
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    //TEST
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    //REFLECTION
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    //DB
    runtimeOnly("com.h2database:h2")
//	runtimeOnly("org.postgresql:postgresql")
    //SECURITY
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3")
    //WebClient
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    //Swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
    // REDIS
    implementation("org.springframework.boot:spring-boot-starter-data-redis:3.2.2")
    //QueryDSL
    implementation("com.querydsl:querydsl-jpa:5.1.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.1.0:jakarta")

    //OAUTH
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    //MAIL
    implementation("org.springframework.boot:spring-boot-starter-mail")
    //Valid
    implementation("org.springframework.boot:spring-boot-starter-validation")
    //JAVA
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.4")
    //AWS
    implementation("org.springframework.cloud:spring-cloud-starter-aws:2.0.1.RELEASE")
    //API
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.0")
    //xml형변환
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.0")
    //WEB
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    //TEST
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    //REFLECTION
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    //DB
    runtimeOnly("com.h2database:h2")
//	runtimeOnly("org.postgresql:postgresql")
    //SECURITY
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3")
    //WebClient
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    //Swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
    // REDIS
    implementation("org.springframework.boot:spring-boot-starter-data-redis:3.2.2")
    //QueryDSL
    implementation("com.querydsl:querydsl-jpa:5.1.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.1.0:jakarta")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
