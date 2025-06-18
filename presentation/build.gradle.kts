plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    // todo:: 20] update here with below plugins
    alias(libs.plugins.screenshot)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.android.junit)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.dataholding.vishal.presentation"
    compileSdk = 35

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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    //todo:: 21] update here with compose true and compiler version

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }

    experimentalProperties["android.experimental.enableScreenshotTest"] = true

}

dependencies {
    // todo:: 23] add :domain :code : core-ui hilt and its compiler
    api(project(":domain"))
    implementation(project(":core"))
    api(project(":core-ui"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.kotlin.reflect)

    testImplementation(libs.bundles.unittest)
    androidTestImplementation(libs.bundles.androidtest)
    testImplementation(libs.turbine)
    screenshotTestImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    androidTestImplementation(libs.androidx.junit)
}