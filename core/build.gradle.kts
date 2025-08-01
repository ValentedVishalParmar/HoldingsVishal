plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "com.dataholding.vishal.core"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    buildFeatures {
        compose = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }

    kotlinOptions {
        jvmTarget = "19"
    }
}

dependencies {

    //CORE
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // MATERIAL
    implementation(libs.material)
    implementation(libs.androidx.material3)
    api(libs.androidx.material.icons.extended)

    // COMPOSE
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.activity.compose)
    api(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.constraintlayout.compose)

    // SERIALIZATION
    api(libs.kotlinx.serialization.json)
    implementation (libs.converter.gson)

    // EXTENDED SPAN
    implementation(libs.extendedspans)

    // NAVIGATION
    implementation (libs.androidx.navigation.compose)

    // GLIDE
    api(libs.glide)

    //HILT DAGGER
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    debugApi(libs.androidx.ui.tooling)

}