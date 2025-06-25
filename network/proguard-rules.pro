# Hilt/Dagger
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-keep class com.google.** { *; }
-keep class * extends dagger.hilt.android.internal.lifecycle.HiltViewModelFactory { *; }

# Retrofit/OkHttp
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }
-keepattributes Signature
-keepattributes *Annotation*

# Room
-keep class androidx.room.** { *; }
-keep class * extends androidx.room.RoomDatabase { *; }
-keepclassmembers class * {
    @androidx.room.* <methods>;
}

# Gson/Serialization
-keep class com.google.gson.** { *; }
-keep class kotlinx.serialization.** { *; }

# Prevent logging in release
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}

# Remove unused code
-dontwarn javax.annotation.**
-dontwarn kotlin.**

# Obfuscate everything else (default) 