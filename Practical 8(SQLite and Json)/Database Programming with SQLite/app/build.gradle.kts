plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.databaseprogrammingwithsqlite"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.databaseprogrammingwithsqlite"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("androidx.recyclerview:recyclerview:1.2.1")  // RecyclerView dependency
    implementation ("androidx.appcompat:appcompat:1.3.1")  // AppCompat for backward compatibility
    implementation ("androidx.constraintlayout:constraintlayout:2.1.0")  // For ConstraintLayout
}