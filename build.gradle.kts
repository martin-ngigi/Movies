// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
    }
    dependencies {
        //val hilt_version = "2.51.1"
       // classpath("com.google.dagger:hilt-android-gradle-plugin:$hilt_version")

    }
}
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("androidx.room") version "2.6.1" apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
    //id("com.google.gms.google-services") version "4.4.2" apply false

}