# ============================================================
# MeetingMind ProGuard / R8 Rules
# ============================================================

# General
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes EnclosingMethod

# ============================================================
# Hilt / Dagger
# ============================================================
-dontwarn dagger.**
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper { *; }
-keepnames @dagger.hilt.android.lifecycle.HiltViewModel class *

# ============================================================
# Data classes used with Gson (remote API models)
# ============================================================
-keep class com.meetingmind.app.data.remote.** { *; }
-keep class com.meetingmind.app.data.local.entity.** { *; }
-keep class com.meetingmind.app.domain.model.** { *; }

# ============================================================
# Gson
# ============================================================
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# ============================================================
# OkHttp
# ============================================================
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

# ============================================================
# Retrofit
# ============================================================
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# ============================================================
# Room
# ============================================================
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# ============================================================
# Jetpack Compose
# ============================================================
-dontwarn androidx.compose.**

# ============================================================
# Apache POI (Word export)
# ============================================================
-dontwarn org.apache.poi.**
-keep class org.apache.poi.** { *; }

# ============================================================
# iText (PDF export)
# ============================================================
-dontwarn com.itextpdf.**
-keep class com.itextpdf.** { *; }

# ============================================================
# Keep BuildConfig (API key)
# ============================================================
-keep class com.meetingmind.app.BuildConfig { *; }

# ============================================================
# Keep MainActivity / Application entry points
# ============================================================
-keep class com.meetingmind.app.MainActivity { *; }
-keep class com.meetingmind.app.MeetingMindApp { *; }
