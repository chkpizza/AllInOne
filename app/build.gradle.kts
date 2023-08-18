plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
}

android {

    compileSdk = Version.compileSdk
    buildToolsVersion = Version.buildToolsVersion

    defaultConfig {
        applicationId = "com.wantique.allinone"
        minSdk = Version.minSdk
        targetSdk = Version.targetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    namespace  = "com.wantique.allinone"
}

dependencies {
    implementation(project(":resource"))

    implementation(AndroidX.CORE)
    implementation(AndroidX.APP_COMAPT)
    implementation(Google.MATERIAL)
    implementation(AndroidX.CONSTRAINT_LAYOUT)
    testImplementation(AndroidTest.JUNIT)
    androidTestImplementation(AndroidTest.EXT_JUNIT)
    androidTestImplementation(AndroidTest.ESPRESSO_CORE)
}