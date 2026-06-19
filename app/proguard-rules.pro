# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.

# Keep Hilt generated classes
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper { *; }

# Keep Room entities
-keep class com.cooking.plan.data.local.** { *; }

# Keep kotlinx.serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep Compose
-keep class androidx.compose.** { *; }

# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

# Keep kotlinx.serialization serializable classes used in AI requests
-keep class com.cooking.plan.data.ai.ChatRequest { *; }
-keep class com.cooking.plan.data.ai.ChatMessage { *; }
-keep class com.cooking.plan.data.ai.ChatResponse { *; }
-keep class com.cooking.plan.data.ai.ChatChoice { *; }
-keep class com.cooking.plan.data.ai.ChatMessageContent { *; }
-keep class com.cooking.plan.data.ai.AiRecipePayload { *; }
-keep class com.cooking.plan.data.ai.AiIngredientPayload { *; }
-keep class com.cooking.plan.data.ai.AiStepPayload { *; }

# Keep generated serializers
-keep,includedescriptorclasses class com.cooking.plan.data.ai.$$serializer { *; }
-keepclassmembers class com.cooking.plan.data.ai.** {
    *** Companion;
}
