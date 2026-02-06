package com.example.flowly.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flowly.model.CollectedItem
import com.example.flowly.ui.theme.CoffeeDark

@Composable
fun CollectionScreen(items: List<CollectedItem>, onBack: () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFFDFBF9)) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text("My Collection", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = CoffeeDark)

            Spacer(modifier = Modifier.height(16.dp))

            if (items.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Nothing collected yet... Get to work! 💪", color = Color.Gray)
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(items) { item ->
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Text(item.icon, fontSize = 32.sp)
                                Spacer(modifier = Modifier.width(16.dp))
                                Column {
                                    Text(item.name, fontWeight = FontWeight.Bold, color = CoffeeDark)
                                    Text("${item.duration} min - ${item.theme.label}", fontSize = 12.sp, color = Color.Gray)
                                }
                            }
                        }
                    }
                }
            }

            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Back to Home")
            }
        }
    }
}