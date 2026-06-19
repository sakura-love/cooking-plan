import java.util.Properties
import java.io.File

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

// Version management
val versionProps = Properties().apply {
    val file = rootProject.file("version.properties")
    if (file.exists()) file.inputStream().use { load(it) }
}

var major = (versionProps.getProperty("VERSION_MAJOR") ?: "1").toInt()
var minor = (versionProps.getProperty("VERSION_MINOR") ?: "0").toInt()
var patch = (versionProps.getProperty("VERSION_PATCH") ?: "0").toInt()

val appVersionName = "$major.$minor.$patch"
val localProps = Properties().apply {
    val f = rootProject.file("local.properties")
    if (f.exists()) f.inputStream().use { load(it) }
}
fun optionalSecret(key: String, env: String): String? {
    return localProps.getProperty(key) ?: System.getenv(env)
}
val hasReleaseSigning = rootProject.file("cooking-plan.keystore").exists() &&
    optionalSecret("cooking.plan.storePassword", "COOKING_PLAN_STORE_PASS") != null &&
    optionalSecret("cooking.plan.keyPassword", "COOKING_PLAN_KEY_PASS") != null

// Auto-increment patch on build
tasks.register("bumpVersion") {
    doLast {
        patch++
        if (patch > 9) { patch = 0; minor++ }
        if (minor > 9) { minor = 0; major++ }
        val newProps = Properties()
        newProps.setProperty("VERSION_MAJOR", major.toString())
        newProps.setProperty("VERSION_MINOR", minor.toString())
        newProps.setProperty("VERSION_PATCH", patch.toString())
        rootProject.file("version.properties").outputStream().use { newProps.store(it, null) }
    }
}

android {
    namespace = "com.cooking.plan"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.cooking.plan"
        minSdk = 26
        targetSdk = 35
        versionCode = major * 10000 + minor * 100 + patch
        versionName = "$major.$minor.$patch"
    }

    signingConfigs {
        if (hasReleaseSigning) {
            create("release") {
                // Read keystore passwords from local.properties or environment variables.
                // Add to local.properties:
                //   cooking.plan.storePassword=xxx
                //   cooking.plan.keyPassword=xxx
                fun prop(key: String, env: String): String {
                    return optionalSecret(key, env)
                        ?: throw GradleException("Missing $key in local.properties or $env env var")
                }
                storeFile = rootProject.file("cooking-plan.keystore")
                storePassword = prop("cooking.plan.storePassword", "COOKING_PLAN_STORE_PASS")
                keyAlias = "cooking-plan"
                keyPassword = prop("cooking.plan.keyPassword", "COOKING_PLAN_KEY_PASS")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            if (hasReleaseSigning) {
                signingConfig = signingConfigs.getByName("release")
            }
        }
        debug {
            isMinifyEnabled = false
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
        compose = true
    }
}

dependencies {
    // Compose BOM
    val composeBom = platform("androidx.compose:compose-bom:2024.10.01")
    implementation(composeBom)
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.8.3")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    ksp("com.google.dagger:hilt-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    // Core
    implementation("androidx.core:core-ktx:1.15.0")

    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

    // OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
}

// Copy APK to releases with custom naming
tasks.register("copyReleaseApkToReleases") {
    doLast {
        val apkDir = layout.buildDirectory.dir("outputs/apk/release")
        val releasesDir = rootProject.file("releases")
        releasesDir.mkdirs()

        val apkFile = apkDir.get().asFile.listFiles()?.firstOrNull { it.name.endsWith(".apk") }
        if (apkFile != null) {
            val dest = File(releasesDir, "料理计划-v$appVersionName.apk")
            apkFile.copyTo(dest, overwrite = true)
            println("Copied release: ${apkFile.name} -> ${dest.name}")
        }
    }
}

tasks.register("copyDebugApkToReleases") {
    doLast {
        val apkDir = layout.buildDirectory.dir("outputs/apk/debug")
        val releasesDir = rootProject.file("releases")
        releasesDir.mkdirs()

        val apkFile = apkDir.get().asFile.listFiles()?.firstOrNull { it.name.endsWith(".apk") }
        if (apkFile != null) {
            val dest = File(releasesDir, "料理计划-v$appVersionName.apk")
            apkFile.copyTo(dest, overwrite = true)
            println("Copied debug: ${apkFile.name} -> ${dest.name}")
        }
    }
}

afterEvaluate {
    tasks.named("assembleRelease") {
        finalizedBy("bumpVersion", "copyReleaseApkToReleases")
    }
    tasks.named("assembleDebug") {
        finalizedBy("copyDebugApkToReleases")
    }
}
