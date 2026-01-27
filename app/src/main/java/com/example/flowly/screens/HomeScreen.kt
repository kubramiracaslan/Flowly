package com.example.flowly.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flowly.R // Paket ismin farklıysa burayı düzeltmen gerekebilir
import com.example.flowly.model.ActivityType
import com.example.flowly.model.FocusConfig
import com.example.flowly.ui.theme.CoffeeDark

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    onStart: (FocusConfig) -> Unit
) {
    // Slider için state (float)
    var sliderValue by remember { mutableFloatStateOf(25f) }
    var selectedActivity by remember { mutableStateOf(ActivityType.STUDY) }

    val durationMinutes = sliderValue.toInt()

    // Kahve hiyerarşisi (Slider'a göre dinamik)
    val coffeeInfo = when {
        durationMinutes < 25 -> "You’ll brew an Espresso ☕"
        durationMinutes < 60 -> "You’ll brew an Americano ☕"
        durationMinutes < 90 -> "You’ll brew a smooth Latte 🥛"
        durationMinutes < 120 -> "You’ll brew a Caramel Macchiato 🍮"
        else -> "You’ll brew a Legendary Frappuccino 🍨"
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFDFBF9)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(15.dp))

            // LOGO (Merkezde)
            Image(
                painter = painterResource(id = R.drawable.logo1), // Dosya ismin logo.png olduğu için
                contentDescription = "Flowly Logo",
                modifier = Modifier
                    .size(250.dp) // Logonun boyutu
                    .padding(bottom = 0.dp)
            )

            Text(
                text = "Are you ready to focus?",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(15.dp))

            // SÜRE GÖSTERGESİ
            Text(
                text = "$durationMinutes min",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = CoffeeDark
            )

            // SLIDER
            Slider(
                value = sliderValue,
                onValueChange = { sliderValue = it },
                valueRange = 5f..150f,
                colors = SliderDefaults.colors(
                    thumbColor = CoffeeDark,
                    activeTrackColor = CoffeeDark,
                    inactiveTrackColor = CoffeeDark.copy(alpha = 0.2f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            // KAHVE BİLGİSİ
            Text(
                text = coffeeInfo,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(48.dp))

            // AKTİVİTE SEÇİMİ
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Select Activity",
                    fontWeight = FontWeight.SemiBold,
                    color = CoffeeDark,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ActivityType.entries.forEach { type ->
                        FilterChip(
                            selected = (selectedActivity == type),
                            onClick = { selectedActivity = type },
                            label = { Text(type.label) },
                            shape = RoundedCornerShape(12.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = CoffeeDark,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // BAŞLAT BUTONU
            Button(
                onClick = { onStart(FocusConfig(durationMinutes, selectedActivity)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CoffeeDark)
            ) {
                Text("Start Brewing", fontSize = 18.sp, color = Color.White)
            }
        }
    }
}