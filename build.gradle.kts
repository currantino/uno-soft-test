plugins {
    id("java")
    application
}

group = "com.currantino"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.jar {
    manifest {
        attributes("Main-Class" to "com.currantino.Main")
    }
}