plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "me.Cutiemango.MangoQuest"
version = "2.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://mvn.lumine.io/repository/maven-public/")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
    compileOnly("io.papermc.paper:paper-api:1.16.5-R0.3-SNAPSHOT")
    compileOnly("org.projectlombok:lombok:1.18.22")
    compileOnly("io.lumine:Mythic-Dist:5.2.1")
    implementation("org.mongodb:mongo-java-driver:3.12.12")
    // https://mvnrepository.com/artifact/net.sf.jopt-simple/jopt-simple
    implementation("net.sf.jopt-simple:jopt-simple:5.0.4")

    compileOnly(fileTree("lib"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}
shadow {
    dependencies{
        implementation("org.mongodb:mongo-java-driver:3.12.12")
        implementation("net.sf.jopt-simple:jopt-simple:5.0.4")
    }
}