package com.example.flowly.screens

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
import com.example.flowly.model.ActivityType
import com.example.flowly.model.FocusConfig
import com.example.flowly.ui.theme.CoffeeDark

@Composable
fun HomeScreen(
    onStart: (FocusConfig) -> Unit  //Starta basınca dışarıya bir focusConfig gönderiliyor. Unit void gibi çalışır.
) {
    var selectedDuration by remember { mutableIntStateOf(25) }
    var selectedActivity by remember { mutableStateOf(ActivityType.STUDY) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFDFBF9)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                "Flowly",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = CoffeeDark
            )
            Text("Ready to focus?", color = Color.Gray)

            Spacer(modifier = Modifier.height(40.dp))

            Text("Select Duration", fontWeight = FontWeight.SemiBold)
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(10, 25, 60).forEach { mins ->
                    FilterChip(
                        selected = (selectedDuration == mins),
                        onClick = { selectedDuration = mins },
                        label = { Text("$mins min") },
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Activity Type", fontWeight = FontWeight.SemiBold)
            FlowRow( // Aktivite butonlarını yan yana dizer
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ActivityType.entries.forEach { type ->
                    FilterChip(
                        selected = (selectedActivity == type),
                        onClick = { selectedActivity = type },
                        label = { Text(type.label) },
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { onStart(FocusConfig(selectedDuration, selectedActivity)) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CoffeeDark)
            ) {
                Text("Start Brewing", fontSize = 18.sp, color = Color.White)
            }
        }
    }
}