plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.hilt)
    alias(libs.plugins.kotlin.safeargs)
    id("kotlin-kapt")
    id ("kotlin-parcelize")
}

android {
    namespace = "com.paradisetaste"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.paradisetaste"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Hilt
    implementation(libs.hilt.android)
    kapt (libs.hilt.compiler)

    //Lifecycle
    //val lifecycle_version = "2.6.2"
    implementation (libs.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.livedata.ktx)
    //Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.okhttp)
    //Coroutine
    implementation (libs.kotlinx.coroutine)
    implementation (libs.kotlinx.android)

    //Nevigation
    val nav_version = "2.4.2"
    implementation ("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation ("androidx.navigation:navigation-ui-ktx:$nav_version")

    //Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    //Glide for Image Loading
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    kapt ("com.github.bumptech.glide:compiler:4.16.0")

    //ViewModel Delegate
    implementation ("androidx.activity:activity-ktx:1.6.0")
    implementation ("androidx.fragment:fragment-ktx:1.6.2")

    // GSON
    implementation ("com.google.code.gson:gson:2.10.1")

    //Responsive
    implementation ("com.intuit.sdp:sdp-android:1.1.1")
    implementation ("com.intuit.ssp:ssp-android:1.1.1")

    //gif
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.17")


}