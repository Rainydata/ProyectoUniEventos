// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    kotlin("android") version "2.0.0" apply false
    id("com.android.application") version "8.5.2" apply false
    id("com.google.gms.google-services") version "4.3.15" apply false
}

allprojects {
    repositories {
        google() // Repositorio de Google
        mavenCentral() // Repositorio de Maven Central
    }
}

buildscript {
    dependencies {
        // Asegúrate de incluir esta dependencia para Google Services
        classpath("com.google.gms:google-services:4.3.15")
    }
}


