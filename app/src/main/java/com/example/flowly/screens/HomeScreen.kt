package com.example.flowly.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flowly.R
import com.example.flowly.model.*
import com.example.flowly.ui.theme.CoffeeDark
import com.example.flowly.ui.theme.CoffeeLight
import kotlinx.coroutines.delay

@Composable
fun MainEntry(onStart: (FocusConfig) -> Unit, onOpenCollection: () -> Unit) {
    var showSplash by remember { mutableStateOf(true) }

    // Uygulama açılışında logo1'in görünmesi
    LaunchedEffect(Unit) {
        delay(2000)
        showSplash = false
    }

    if (showSplash) {
        Box(Modifier.fillMaxSize().background(Color.White), Alignment.Center) {
            Image(painterResource(id = R.drawable.logo1), null, Modifier.size(220.dp))
        }
    } else {
        HomeScreen(onStart, onOpenCollection)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(onStart: (FocusConfig) -> Unit, onOpenCollection: () -> Unit) {
    var sliderValue by remember { mutableFloatStateOf(25f) }
    var selectedActivity by remember { mutableStateOf(ActivityType.STUDY) }
    var selectedTheme by remember { mutableStateOf(ThemeType.COFFEE) }

    val durationMinutes = sliderValue.toInt()
    val previewItem = remember(durationMinutes, selectedTheme) {
        getProducedItem(FocusConfig(durationMinutes, selectedActivity, selectedTheme))
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF7F2EE)) {
        Column(modifier = Modifier.fillMaxSize()) {

            // --- HEADER: SADECE KOLEKSİYON BUTONU ---
            Row(
                Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onOpenCollection) {
                    Icon(Icons.Default.History, null, tint = CoffeeDark, modifier = Modifier.size(28.dp))
                }
            }

            // --- ANA KONTEYNER ---
            Card(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 1. Ödül Kartı
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        color = Color(0xFFFDFBF9)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AnimatedContent(targetState = previewItem.name, label = "img") { name ->
                                Image(painterResource(id = getProductImageRes(name)), null, Modifier.size(60.dp))
                            }
                            Spacer(Modifier.width(16.dp))
                            Column {
                                Text("Focus Reward:", fontSize = 12.sp, color = Color.Gray)
                                AnimatedContent(targetState = previewItem.name, label = "txt") { name ->
                                    Text(name, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = CoffeeDark)
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    // 2. Süre ve Slider
                    Text("${durationMinutes} min", fontSize = 32.sp, fontWeight = FontWeight.Black, color = CoffeeDark)
                    Slider(
                        value = sliderValue,
                        onValueChange = { sliderValue = it },
                        valueRange = 5f..150f,
                        colors = SliderDefaults.colors(thumbColor = CoffeeDark, activeTrackColor = CoffeeDark)
                    )

                    Spacer(Modifier.height(20.dp))

                    // 3. Tema Seçimi
                    Text("Select Theme", Modifier.align(Alignment.Start), fontWeight = FontWeight.Bold, color = CoffeeDark)
                    Row(Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        CustomGridButton(text = "Coffee Shop", isSelected = selectedTheme == ThemeType.COFFEE, Modifier.weight(1f)) { selectedTheme = ThemeType.COFFEE }
                        CustomGridButton(text = "Bakery Hut", isSelected = selectedTheme == ThemeType.BAKERY, Modifier.weight(1f)) { selectedTheme = ThemeType.BAKERY }
                    }

                    Spacer(Modifier.height(20.dp))

                    // 4. Aktivite Seçimi
                    Text("Focus Activity", Modifier.align(Alignment.Start), fontWeight = FontWeight.Bold, color = CoffeeDark)
                    Column(Modifier.padding(top = 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        ActivityType.entries.chunked(2).forEach { row ->
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                row.forEach { act ->
                                    CustomGridButton(text = act.label, isSelected = selectedActivity == act, Modifier.weight(1f)) { selectedActivity = act }
                                }
                                if (row.size == 1) Spacer(Modifier.weight(1f))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // 5. Focus Butonu
                    Button(
                        onClick = { onStart(FocusConfig(durationMinutes, selectedActivity, selectedTheme)) },
                        modifier = Modifier.fillMaxWidth().height(60.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E1A16)),
                        elevation = ButtonDefaults.buttonElevation(6.dp)
                    ) {
                        Text("Start Focusing", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun CustomGridButton(text: String, isSelected: Boolean, modifier: Modifier, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = modifier.height(50.dp),
        shape = RoundedCornerShape(14.dp),
        color = if (isSelected) CoffeeDark else Color(0xFFF8F9FA),
        border = if (!isSelected) BorderStroke(1.dp, Color(0xFFE9ECEF)) else null,
        tonalElevation = if (isSelected) 4.dp else 0.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text, color = if (isSelected) Color.White else CoffeeDark, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}

fun getProductImageRes(itemName: String): Int {
    return when (itemName) {
        "Espresso" -> R.drawable.espresso
        "Americano" -> R.drawable.americano
        "Latte" -> R.drawable.latte
        "Caramel Macchiato" -> R.drawable.caramel_macchiato
        "Legendary Coffee Feast" -> R.drawable.legendary_coffee_feast
        "Cookie" -> R.drawable.cookie
        "Croissant" -> R.drawable.croissant
        "Piece of Cake" -> R.drawable.piece_of_cake
        "Whole Cake" -> R.drawable.cake
        "Legendary Bakery Feast" -> R.drawable.legendary_cake_tower
        else -> R.drawable.logo1 // Fallback olarak logo1
    }
}