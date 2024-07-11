// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.google.gms.google.services) apply false

    alias(libs.plugins.navigation.safe.args) apply false
    alias(libs.plugins.org.jetbrains.kotlin.kapt) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
}