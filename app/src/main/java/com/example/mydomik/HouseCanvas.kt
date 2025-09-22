package com.example.mydomik

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun HouseCanvas(vm: HouseViewModel, modifier: Modifier = Modifier) {
    val colors = mapOf(
        "WHITE" to Color.White,
        "RED" to Color(0xFFB71C1C),
        "BLUE" to Color(0xFF1565C0),
        "YELLOW" to Color(0xFFFFF59D),
        "GREEN" to Color(0xFF2E7D32),
        "BROWN" to Color(0xFF8D6E63),
        "BLACK" to Color.Black,
        "LIGHT" to Color(0xFFD7CCC8),
        "DARK" to Color(0xFF5D4037)
    )

    Box(modifier = modifier.background(Color.Transparent)) {
        Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, rotation ->
                    // rotation only around Y simulated by horizontal pan
                    vm.rotationY += pan.x / 4f
                    vm.scale = (vm.scale * zoom).coerceIn(0.5f, 2.0f)
                }
            }
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            // center
            val cx = canvasWidth / 2f
            val cy = canvasHeight / 2f

            // sizes based on spec (px units). We'll scale to available size.
            val baseScale = (minOf(canvasWidth/400f, canvasHeight/400f)) * vm.scale

            val wallW = 200f * baseScale
            val wallH = 150f * baseScale
            val wallD = 200f * baseScale
            val roofH = 100f * baseScale
            val doorW = 70f * baseScale
            val doorH = 120f * baseScale
            val windowS = 50f * baseScale

            // simple pseudo-3D: shift x by rotation angle
            val angle = Math.toRadians(vm.rotationY.toDouble())
            val dx = (sin(angle) * 40f * baseScale).toFloat()

            // draw back wall (as rectangle shifted)
            val left = cx - wallW/2 + dx
            val top = cy - wallH/2
            drawRect(color = colors[vm.wallColor] ?: Color.White, topLeft = Offset(left, top), size = androidx.compose.ui.geometry.Size(wallW, wallH))

            // roof as triangle
            val p = Path().apply {
                moveTo(cx - wallW/2, top)
                lineTo(cx + wallW/2, top)
                lineTo(cx, top - roofH)
                close()
            }
            drawPath(path = p, color = colors[vm.roofColor] ?: Color.Red)

            // windows (two)
            val winY = top + wallH/4
            val winXOffset = wallW/4
            for (i in listOf(-1, 1)) {
                val wx = cx + i*winXOffset - windowS/2 + dx/1.5f
                if (vm.windowShapeSquare) {
                    drawRect(color = Color(0xFFBBDEFB), topLeft = Offset(wx, winY), size = androidx.compose.ui.geometry.Size(windowS, windowS))
                    // frame
                    drawRect(color = Color.White, topLeft = Offset(wx+4f, winY+4f), size = androidx.compose.ui.geometry.Size(windowS-8f, windowS-8f))
                } else {
                    // round window: draw circle
                    drawCircle(color = Color(0xFFBBDEFB), center = Offset(wx+windowS/2, winY+windowS/2), radius = windowS/2)
                }
            }

            // door centered
            val doorLeft = cx - doorW/2 + dx/2
            val doorTop = top + wallH - doorH
            drawRect(color = colors[vm.doorColor] ?: Color(0xFF8D6E63), topLeft = Offset(doorLeft, doorTop), size = androidx.compose.ui.geometry.Size(doorW, doorH))
        }

        // overlay controls when element selected
        if (vm.selected != null) {
            Surface(modifier = Modifier
                .align(Alignment.Center)
                .padding(12.dp), elevation = 8.dp) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = "Настройки: ${'$'}{vm.selected}")
                    // simple setting buttons (for demo)
                    Button(onClick = {
                        when(vm.selected) {
                            Element.WALLS -> vm.wallColor = "BLUE"
                            Element.ROOF -> vm.roofColor = "BROWN"
                            Element.WINDOWS -> vm.windowShapeSquare = !vm.windowShapeSquare
                            Element.DOOR -> vm.doorColor = "LIGHT"
                            else -> {}
                        }
                    }) {
                        Text("Применить тестовое изменение")
                    }
                    Button(onClick = { vm.selected = null }) {
                        Text("Закрыть")
                    }
                }
            }
        }
    }
}
