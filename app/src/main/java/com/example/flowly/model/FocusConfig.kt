package com.example.flowly.model
//HomeScreen'de seçilen ayarlar tek pakette (FocusConfig) FocusScreen'e taşınır

//Enum class = seçenek listesi
enum class ActivityType(val label: String) {
    STUDY("Study"),
    SPORT("Sport"),
    MEDITATION("Meditation"),
    CLEANING("Cleaning")
}

//data class = veri taşımak için
data class FocusConfig(
    val durationMinutes: Int,
    val activity: ActivityType
)
