package com.example.flowly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.flowly.model.FocusConfig
import com.example.flowly.screens.FocusScreen
import com.example.flowly.screens.HomeScreen
import com.example.flowly.ui.theme.FlowlyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlowlyTheme {
                // currentConfig null ise ana ekrandayız, dolu ise focus ekranındayız
                var currentConfig by remember { mutableStateOf<FocusConfig?>(null) }

                if (currentConfig == null) {
                    HomeScreen(onStart = { config ->
                        currentConfig = config
                    })
                } else {
                    FocusScreen(
                        config = currentConfig!!,
                        onCancel = { currentConfig = null }
                    )
                }
            }
        }
    }
}