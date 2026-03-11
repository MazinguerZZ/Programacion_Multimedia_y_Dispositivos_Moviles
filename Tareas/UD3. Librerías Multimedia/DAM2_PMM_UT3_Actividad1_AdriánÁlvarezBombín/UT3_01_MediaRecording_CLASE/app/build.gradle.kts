plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.pmm.ut3_01_mediarecording_clase"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.pmm.ut3_01_mediarecording_clase"
        minSdk = 24
        targetSdk = 36
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


    // CameraX core
    implementation("androidx.camera:camera-camera2:1.5.2")
    // Integración con Lifecycle (para bindToLifecycle)
    implementation("androidx.camera:camera-lifecycle:1.5.2")
    // Vista de cámara (PreviewView)
    implementation("androidx.camera:camera-view:1.5.2")
}