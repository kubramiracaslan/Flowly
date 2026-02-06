package com.example.flowly

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.flowly.model.*
import com.example.flowly.screens.*
import com.example.flowly.ui.theme.FlowlyTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FlowlyTheme {
                // 1. Uygulama açıldığında kayıtlı verileri yükle
                var collection by remember {
                    mutableStateOf(loadCollectionFromDisk(this@MainActivity))
                }

                var currentConfig by remember { mutableStateOf<FocusConfig?>(null) }
                var currentScreen by remember { mutableStateOf("home") }

                when (currentScreen) {
                    "home" -> HomeScreen(
                        onStart = { config ->
                            currentConfig = config
                            currentScreen = "focus"
                        },
                        onOpenCollection = { currentScreen = "collection" }
                    )

                    "focus" -> FocusScreen(
                        config = currentConfig!!,
                        onCancel = { currentScreen = "home" },
                        onFinish = { producedItem ->
                            // 2. Yeni ürünü listeye ekle
                            val updatedList = collection + producedItem
                            collection = updatedList

                            // 3. Yeni listeyi telefon hafızasına kaydet
                            saveCollectionToDisk(this@MainActivity, updatedList)

                            currentScreen = "collection"
                        }
                    )

                    "collection" -> CollectionScreen(
                        items = collection,
                        onBack = { currentScreen = "home" }
                    )
                }
            }
        }
    }

    // VERİ KAYDETME MANTIĞI
    private fun saveCollectionToDisk(context: Context, items: List<CollectedItem>) {
        val sharedPreferences = context.getSharedPreferences("flowly_storage", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(items) // Listeyi JSON formatında String'e çevirir
        editor.putString("user_collection", json)
        editor.apply()
    }

    // VERİ YÜKLEME MANTIĞI
    private fun loadCollectionFromDisk(context: Context): List<CollectedItem> {
        val sharedPreferences = context.getSharedPreferences("flowly_storage", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("user_collection", null)

        return if (json != null) {
            val gson = Gson()
            val type = object : TypeToken<List<CollectedItem>>() {}.type
            gson.fromJson(json, type) // String'i tekrar List<CollectedItem> haline getirir
        } else {
            emptyList() // Eğer henüz kayıt yoksa boş liste döndürür
        }
    }
}