package com.example.mydomik.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColors = lightColors(
    primary = BlueGradient,
    background = White,
    surface = White
)

@Composable
fun MyDomikTheme(content: @Composable () -> Unit) {
    MaterialTheme(colors = LightColors, content = content)
}
