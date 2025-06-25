# Troubleshooting AGP 8.11.0 and Kotlin 2.2.0 Build Issues

## Issue: KSP Processing Error

If you're getting KSP processing errors after upgrading to AGP 8.11.0 and Kotlin 2.2.0, try these steps:

## Step 1: Clean and Rebuild

```bash
# Clean the project
./gradlew clean

# Clear Gradle cache
./gradlew cleanBuildCache

# Rebuild
./gradlew build
```

## Step 2: Invalidate Caches (Android Studio)

1. Go to `File` → `Invalidate Caches and Restart`
2. Select `Invalidate and Restart`
3. Wait for Android Studio to restart

## Step 3: Update Gradle Wrapper

Make sure you have the latest Gradle wrapper:

```bash
./gradlew wrapper --gradle-version 8.11
```

## Step 4: Check Java Version

Ensure you're using Java 19 or higher:

```bash
java -version
```

## Step 5: Sync Project

1. Go to `File` → `Sync Project with Gradle Files`
2. Or click the "Sync Now" button in the toolbar

## Step 6: Check Module Dependencies

If the issue persists, try building modules individually:

```bash
# Build core module only
./gradlew :core:build

# Build data module only
./gradlew :data:build

# Build presentation module only
./gradlew :presentation:build
```

## Step 7: Alternative KSP Version

If KSP is still causing issues, try this alternative version in `gradle/libs.versions.toml`:

```toml
ksp = "2.2.0-1.0.17"
```

## Step 8: Check for Specific Error Messages

Look for specific error messages in the build output. Common issues:

1. **Annotation Processing**: Some libraries might not be compatible with Kotlin 2.2.0
2. **Hilt Issues**: Make sure Hilt version is compatible
3. **Room Issues**: Check Room compiler compatibility

## Step 9: Temporary Workaround

If the issue persists, you can temporarily disable KSP for specific modules:

```kotlin
// In build.gradle.kts, comment out KSP plugin temporarily
// alias(libs.plugins.kotlin.ksp)
```

## Step 10: Check Android Studio Version

Make sure you're using Android Studio Hedgehog (2023.1.1) or later for AGP 8.11.0 compatibility.

## Common Solutions

### Solution 1: Update Hilt Version
```toml
hiltAndroid = "2.56.2"
daggerHilt = "2.56.2"
```

### Solution 2: Update Room Version
```toml
roomCompiler = "2.7.2"
```

### Solution 3: Add Build Features
```kotlin
buildFeatures {
    compose = true
    buildConfig = true
}
```

### Solution 4: Update Gradle Properties
```properties
android.enableR8.fullMode=false
android.enableBuildConfigAsBytecode=true
android.suppressUnsupportedCompileSdk=35
```

## If Nothing Works

1. **Downgrade temporarily**: Use AGP 8.10.1 and Kotlin 2.1.21 until the issue is resolved
2. **Check GitHub Issues**: Look for similar issues in the Android Gradle Plugin repository
3. **Report the Issue**: If it's a bug, report it to the Android team

## Success Indicators

The build should work if you see:
- ✅ All modules compile successfully
- ✅ KSP processes annotations without errors
- ✅ No dependency conflicts
- ✅ Clean build output

## Additional Resources

- [AGP 8.11.0 Release Notes](https://developer.android.com/studio/releases/gradle-plugin)
- [Kotlin 2.2.0 Release Notes](https://kotlinlang.org/docs/releases.html)
- [KSP Compatibility Matrix](https://github.com/google/ksp#compatibility) 