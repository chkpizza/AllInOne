plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.wantique.auth"
    compileSdk = Version.compileSdk

    defaultConfig {
        minSdk = Version.minSdk
        targetSdk = Version.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
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
}

dependencies {
    implementation(project(":resource"))
    implementation(project(":base"))
    implementation(project(":firebase"))
    implementation(AndroidX.CORE)
    implementation(AndroidX.APP_COMAPT)
    implementation(Google.MATERIAL)
    testImplementation(AndroidTest.JUNIT)
    androidTestImplementation(AndroidTest.EXT_JUNIT)
    androidTestImplementation(AndroidTest.ESPRESSO_CORE)
    implementation(AndroidX.NAVIGATION_UI)
    implementation(AndroidX.NAVIGATION_FRAGMENT)
    implementation(ThirdParty.DAGGER)
    kapt(ThirdParty.DAGGER_COMPILER)
    implementation(ThirdParty.SQUIRCLE_IMAGE_VIEW)
    implementation(ThirdParty.GLIDE)
    implementation(Firebase.FIREBASE_ANALYTICS)
    implementation(Firebase.FIREBASE_AUTH)
    implementation(Google.PLAY_SERVICE_AUTH)
    implementation(ThirdParty.GOOGLE_SIGN_IN_BUTTON)
    implementation(AndroidX.LIVEDATA)
}