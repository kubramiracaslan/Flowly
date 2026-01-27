package com.example.flowly.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flowly.model.FocusConfig
import com.example.flowly.ui.theme.CoffeeDark
import kotlinx.coroutines.delay
import androidx.compose.animation.core.animateFloatAsState
import com.example.flowly.ui.theme.CoffeeLight
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.animation.core.tween

@Composable
fun FocusScreen(
    config: FocusConfig,
    onCancel: () -> Unit
) {
    var timeLeft by remember { mutableIntStateOf(config.durationMinutes * 60) }
    val totalSeconds = config.durationMinutes * 60

    // Geri sayım mantığı
    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            delay(1000)
            timeLeft--
        }
    }

    val progress = 1f - (timeLeft.toFloat() / totalSeconds)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = config.activity.label.uppercase(),
            style = MaterialTheme.typography.labelLarge,
            color = Color.Gray,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Evrim Görseli Alanı (Şimdilik metin olarak evrimleşiyor)
        EvolutionGraphic(progress, config.durationMinutes)

        Spacer(modifier = Modifier.height(40.dp))

        // Sayaç
        val minutes = timeLeft / 60
        val seconds = timeLeft % 60
        Text(
            text = String.format("%02d:%02d", minutes, seconds),
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            color = CoffeeDark
        )

        Spacer(modifier = Modifier.height(60.dp))

        OutlinedButton(
            onClick = onCancel,
            // Border (kenarlık) ve Text rengini CoffeeDark yapıyoruz
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = CoffeeDark
            ),
            border = androidx.compose.foundation.BorderStroke(1.dp, CoffeeDark),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                "Give Up",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun EvolutionGraphic(progress: Float, duration: Int) {
    // Debug için progress değerini console'a yazdırabilirsin:
    // println("Gelen Progress: $progress")

    val stage = when {
        progress < 0.25f -> "🌱 Coffee Bean"
        progress < 0.55f -> "⚙️ Grinding..."
        progress < 0.85f -> "☕ Brewing..."
        else -> {
            // Süre tamamlanmaya yakınken veya tamamlandığında görünecek kahve ismi
            when {
                duration < 25 -> "☕ Espresso"
                duration < 60 -> "☕ Americano"
                duration < 90 -> "🥛 Latte"
                duration < 120 -> "🍮 Caramel Macchiato"
                else -> "🍨 Frappuccino"
            }
        }
    }

    // Animasyonlu progress
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 500),
        label = "progress"
    )

    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(250.dp)) {
        CircularProgressIndicator(
            progress = { 1f },
            modifier = Modifier.fillMaxSize(),
            color = CoffeeLight.copy(alpha = 0.3f),
            strokeWidth = 8.dp,
            strokeCap = StrokeCap.Round
        )

        CircularProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier.fillMaxSize(),
            color = CoffeeDark,
            strokeWidth = 12.dp,
            strokeCap = StrokeCap.Round
        )

        // Text kısmını Crossfade ile sarmaladım
        // buradaki 'text' değişkeninin boş gelmediğinden emin ol
        Crossfade(targetState = stage, label = "stageAnimation") { currentStage ->
            Text(
                text = currentStage,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = CoffeeDark
            )
        }
    }
}
