package com.example.flowly.model
//HomeScreen'de seçilen ayarlar tek pakette (FocusConfig) FocusScreen'e taşınır

//Enum class = seçenek listesi
enum class ActivityType(val label: String) {
    STUDY("Study"),
    SPORT("Sport"),
    MEDITATION("Meditation"),
    CLEANING("Cleaning")
}

enum class ThemeType(val label: String) {
    COFFEE("Coffee Shop"),
    BAKERY("Sweet Bakery")
}
//data class = veri taşımak için
data class FocusConfig(
    val durationMinutes: Int,
    val activity: ActivityType,
    val theme: ThemeType
)
data class CollectedItem(
    val name: String,
    val icon: Int, // Ekranda emoji veya basit ikon göstermek için
    val theme: ThemeType,
    val duration: Int,
    val date: String // Yeni eklenen alan
)