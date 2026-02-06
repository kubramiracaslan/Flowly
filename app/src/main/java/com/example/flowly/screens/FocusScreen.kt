package com.example.flowly.screens

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flowly.R
import com.example.flowly.model.*
import com.example.flowly.ui.theme.CoffeeDark
import com.example.flowly.ui.theme.CoffeeLight
import kotlinx.coroutines.delay
import java.util.*

@Composable
fun FocusScreen(
    config: FocusConfig,
    onCancel: () -> Unit,
    onFinish: (CollectedItem) -> Unit
) {
    var timeLeft by remember { mutableIntStateOf(config.durationMinutes * 60) }
    val totalSeconds = config.durationMinutes * 60
    var showExitDialog by remember { mutableStateOf(false) }
    val producedItem = remember { getProducedItem(config) }

    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            delay(1000)
            timeLeft--
        }
        onFinish(producedItem)
    }

    val progress = 1f - (timeLeft.toFloat() / totalSeconds)

    // --- Give Up Onay Diyaloğu ---
    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Are you sure?", fontWeight = FontWeight.Bold, color = CoffeeDark) },
            text = { Text("Your ${producedItem.name} was almost ready! Do you really want to lose your progress? 🥺") },
            confirmButton = {
                TextButton(onClick = onCancel) { Text("Yes, Give Up", color = Color.Red) }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) { Text("Keep Going") }
            },
            shape = RoundedCornerShape(16.dp),
            containerColor = Color.White
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = config.activity.label.uppercase(),
            color = Color.Gray,
            letterSpacing = 4.sp,
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Görsel ve Progress Alanı
        EvolutionGraphic(progress, config)

        Spacer(modifier = Modifier.height(40.dp))

        // Zamanlayıcı Tasarımı
        val minutes = timeLeft / 60
        val seconds = timeLeft % 60
        Text(
            text = String.format(Locale.ENGLISH, "%02d:%02d", minutes, seconds),
            style = MaterialTheme.typography.displayLarge.copy(
                fontWeight = FontWeight.Light, // Daha zarif bir görünüm için ince font
                fontSize = 80.sp
            ),
            color = CoffeeDark
        )

        Spacer(modifier = Modifier.height(60.dp))

        // Estetik Vazgeçme Butonu
        TextButton(onClick = { showExitDialog = true }) {
            Text(
                "Give Up",
                color = Color.Gray,
                textDecoration = TextDecoration.Underline,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun EvolutionGraphic(progress: Float, config: FocusConfig) {
    // Görsel Seçim Mantığı (Hazırladığın isimlere göre)
    val currentImageRes = when (config.theme) {
        ThemeType.COFFEE -> when {
            progress < 0.33f -> R.drawable.coffee_bean // Preparing
            progress < 0.66f -> R.drawable.coffee_grinding // Grinding
            else -> R.drawable.coffee_brewing            // Brewing
        }
        ThemeType.BAKERY -> when {
            progress < 0.33f -> R.drawable.dough_mixing// Mixing
            progress < 0.66f -> R.drawable.dough_kneading // Kneading
            else -> R.drawable.dough_baking             // Baking
        }
    }

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(1000),
        label = "progressAnimation"
    )

    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(280.dp)) {
        // Arka plandaki sönük çember
        CircularProgressIndicator(
            progress = { 1f },
            modifier = Modifier.fillMaxSize(),
            color = CoffeeLight.copy(alpha = 0.2f),
            strokeWidth = 6.dp
        )

        // İlerleyen ana çember
        CircularProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier.fillMaxSize(),
            color = CoffeeDark,
            strokeWidth = 10.dp,
            strokeCap = StrokeCap.Round
        )

        // Görsel Geçişi (Crossfade ile yumuşak geçiş)
        Crossfade(targetState = currentImageRes, animationSpec = tween(1000), label = "imageFade") { resId ->
            Image(
                painter = painterResource(id = resId),
                contentDescription = null,
                modifier = Modifier.size(160.dp) // Halkanın ortasına uygun boyut
            )
        }
    }
}

fun getProducedItem(config: FocusConfig): CollectedItem {
    return when (config.theme) {
        ThemeType.COFFEE -> when {
            config.durationMinutes < 25 -> CollectedItem("Espresso", "☕", config.theme, config.durationMinutes)
            config.durationMinutes < 60 -> CollectedItem("Americano", "☕", config.theme, config.durationMinutes)
            config.durationMinutes < 90 -> CollectedItem("Latte", "🥛", config.theme, config.durationMinutes)
            config.durationMinutes < 120 -> CollectedItem("Caramel Macchiato", "🍮", config.theme, config.durationMinutes)
            else -> CollectedItem("Legendary Coffee Feast", "☕🍮🥛✨", config.theme, config.durationMinutes)
        }
        ThemeType.BAKERY -> when {
            config.durationMinutes < 25 -> CollectedItem("Cookie", "🍪", config.theme, config.durationMinutes)
            config.durationMinutes < 60 -> CollectedItem("Croissant", "🥐", config.theme, config.durationMinutes)
            config.durationMinutes < 90 -> CollectedItem("Piece of Cake", "🍰", config.theme, config.durationMinutes)
            config.durationMinutes < 120 -> CollectedItem("Whole Cake", "🎂", config.theme, config.durationMinutes)
            else -> CollectedItem("Legendary Bakery Feast", "🎂🍰🥯🥨🧁✨", config.theme, config.durationMinutes)
        }
    }
}