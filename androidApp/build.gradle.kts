plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.noorapp.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.example.noorapp.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
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
    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.3.1")
    implementation("androidx.compose.ui:ui-tooling:1.3.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.1")
    implementation("androidx.compose.foundation:foundation:1.3.1")
    implementation("androidx.compose.material:material:1.3.1")
    implementation("androidx.compose.material:material-icons-extended:1.4.0-rc01")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("io.insert-koin:koin-androidx-compose:3.3.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("io.insert-koin:koin-android:3.3.0")
    implementation("com.google.firebase:firebase-auth-ktx:21.1.0")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0-alpha08")
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation("androidx.media3:media3-exoplayer:1.0.0-rc02")
    implementation("androidx.media3:media3-ui:1.0.0-rc02")
    implementation ("com.google.firebase:firebase-storage-ktx:20.1.0")
    implementation ("com.google.firebase:firebase-firestore-ktx:24.4.4")
    compileOnly("io.realm.kotlin:library-base:1.6.0")

}