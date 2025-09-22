package com.example.mydomik

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.mydomik.ui.theme.MyDomikTheme

class MainActivity : ComponentActivity() {
    private val vm: HouseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.loadFromPreferences(this)
        setContent {
            MyDomikTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppContent(vm = vm)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        vm.saveToPreferences(this)
    }
}

@Composable
fun AppContent(vm: HouseViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Top bar
        TopAppBar(
            title = { Text("Мой Домик") },
            actions = {
                TextButton(onClick = { vm.reset() }) {
                    Text("Сбросить")
                }
            },
            backgroundColor = MaterialTheme.colors.surface,
            elevation = 0.dp
        )
        // Content background gradient
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
            .background(Brush.verticalGradient(listOf(MaterialTheme.colors.primary, MaterialTheme.colors.background)))
        ) {
            HouseCanvas(vm = vm, modifier = Modifier.fillMaxSize())
        }
        // Bottom panel
        BottomPanel(vm = vm, modifier = Modifier.fillMaxWidth().padding(12.dp))
    }
}
