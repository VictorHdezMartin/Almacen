plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.example.amacen"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.amacen"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}


dependencies {

 implementation(libs.filament.android)

// dependencias RETROFIT
    val retroFitVersion = "2.11.0"
    implementation("com.squareup.retrofit2:retrofit:$retroFitVersion")                              // RetroFit
    implementation("com.squareup.retrofit2:converter-gson:$retroFitVersion")                        // Gson

// dependencia PICASSO
    implementation("com.squareup.picasso:picasso:2.8")                                              // Picasso

// dependencia para las notificaciones
  //  implementation("androidx.core:core:2.2.0")

// dependencias sqLITE
  //  implementation(libs.androidx.sqlite)                      //  hay que copiarlo fichero: 'libs.versions.toml' -> androidx-sqlite = { group = "androidx.sqlite", name = "sqlite", version.ref = "sqlite" }

// Propias de ANDROID
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}