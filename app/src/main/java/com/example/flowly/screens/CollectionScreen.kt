package com.example.flowly.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flowly.R
import com.example.flowly.model.CollectedItem
import com.example.flowly.ui.theme.CoffeeDark

@Composable
fun CollectionScreen(items: List<CollectedItem>, onBack: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Pixel Art Arka Plan
        Image(
            painter = painterResource(id = R.drawable.collections_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 2. İçerik
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = "My Collection",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (items.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Nothing collected yet... Get to work! 💪",
                        color = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier
                            .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    )
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(items) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White.copy(alpha = 0.9f)
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Ürün İkonu (Cookie, Coffee vb.)
                                Image(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = item.name,
                                    modifier = Modifier.size(48.dp)
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    // Ürün İsmi
                                    Text(item.name, fontWeight = FontWeight.Bold, color = CoffeeDark)
                                    // Süre ve Tema
                                    Text(
                                        text = "${item.duration} min - ${item.theme.label}",
                                        fontSize = 12.sp,
                                        color = Color.DarkGray
                                    )
                                }

                                // --- TARİH  ---
                                // Eğer CollectedItem modelinde 'date' alanı varsa burada gösteriyoruz
                                Text(
                                    text = item.date, // Örn: "Feb 6"
                                    fontSize = 11.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.align(Alignment.Bottom)
                                )
                            }
                        }
                    }
                }
            }

            Button(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CoffeeDark)
            ) {
                Text("Back to Home", color = Color.White)
            }
        }
    }
}