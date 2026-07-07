# Android Runtime (ART) and Java 6 bytecode compatibility
-keep class com.rjnx.hyperengine.** { *; }
-keep class androidx.** { *; }
-keep class com.google.dagger.** { *; }
-keep class com.google.dagger.hilt.** { *; }

# Keep Room database classes
-keep class androidx.room.** { *; }
-keep class * extends androidx.room.Database { *; }
-keep class * extends androidx.room.Entity { *; }
-keep class * extends androidx.room.Dao { *; }

# Keep DataStore
-keep class androidx.datastore.** { *; }

# Keep Navigation
-keep class androidx.navigation.** { *; }

# Keep Compose
-keep class androidx.compose.** { *; }

# Keep Kotlin
-keep class kotlin.** { *; }
-keep class kotlinx.** { *; }

# Keep R classes
-keep class **.R$* { *; }

# Keep all activities
-keep class * extends android.app.Activity

# Keep all fragments
-keep class * extends android.app.Fragment

# Keep all view models
-keep class * extends androidx.lifecycle.ViewModel

# Keep all services
-keep class * extends android.app.Service

# Keep all broadcast receivers
-keep class * extends android.content.BroadcastReceiver

# Keep all content providers
-keep class * extends android.content.ContentProvider

# Keep Parcelable classes
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
