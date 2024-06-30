plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kapt)
}

android {
    namespace = "com.lesa.ui"
    compileSdk = libs.versions.androidSdk.compile.get().toInt()
    defaultConfig {
        minSdk = libs.versions.androidSdk.min.get().toInt()
    }
    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // Base:
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.lottie.compose)

    // DI:
    implementation(libs.hilt.android)
    implementation(libs.javax.inject)
    implementation(libs.androidx.hilt.navigation)
    kapt(libs.hilt.compiler)

    // Modules:
    implementation(project(":uikit"))
    implementation(project(":features:persons:ui_logic"))
    implementation(project(":navigation"))
}
