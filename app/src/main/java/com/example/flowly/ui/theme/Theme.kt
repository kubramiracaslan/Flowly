package com.example.flowly.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White


private val LightColorScheme = lightColorScheme(
    primary = CoffeeDark,        // Ana buton rengi
    secondary = CoffeeLight,     // İkincil öğeler
    background = BackgroundCream, // Arka plan
    surface = White,             // Kartların üzeri
    onPrimary = Color.White      // Buton üzerindeki yazı rengi
)

@Composable
fun FlowlyTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography, // Eğer Typography hata verirse bunu MaterialTheme.typography yapabilirsin
        content = content
    )
}