plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id("com.google.gms.google-services")
}

android {
    compileSdk = Version.compileSdk
    buildToolsVersion = Version.buildToolsVersion

    defaultConfig {
        applicationId = "com.wantique.allinone"
        minSdk = Version.minSdk
        targetSdk = Version.targetSdk
        versionCode = 5
        versionName = "1.0.1"

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

    buildFeatures {
        dataBinding = true
    }

    namespace  = "com.wantique.allinone"
}

dependencies {
    implementation(project(":resource"))
    implementation(project(":base"))
    implementation(project(":firebase"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:home"))
    implementation(project(":feature:daily"))
    implementation(project(":feature:mypage"))

    implementation(AndroidX.CORE)
    implementation(AndroidX.APP_COMAPT)
    implementation(Google.MATERIAL)
    implementation(AndroidX.CONSTRAINT_LAYOUT)
    testImplementation(AndroidTest.JUNIT)
    androidTestImplementation(AndroidTest.EXT_JUNIT)
    androidTestImplementation(AndroidTest.ESPRESSO_CORE)
    implementation(AndroidX.NAVIGATION_UI)
    implementation(AndroidX.NAVIGATION_FRAGMENT)
    implementation(ThirdParty.DAGGER)
    kapt(ThirdParty.DAGGER_COMPILER)

}