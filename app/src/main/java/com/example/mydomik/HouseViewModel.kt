package com.example.mydomik

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HouseViewModel : ViewModel() {
    // rotation around Y (degrees)
    var rotationY by mutableStateOf(0f)
    // scale for zoom
    var scale by mutableStateOf(1f)

    // properties
    var wallColor by mutableStateOf("WHITE")
    var roofColor by mutableStateOf("RED")
    var windowShapeSquare by mutableStateOf(true)
    var doorColor by mutableStateOf("BROWN")

    // selection
    var selected by mutableStateOf<Element?>(null)

    private val PREFS = "mydomik_prefs"

    fun reset() {
        rotationY = 0f
        scale = 1f
        wallColor = "WHITE"
        roofColor = "RED"
        windowShapeSquare = true
        doorColor = "BROWN"
        selected = null
    }

    fun saveToPreferences(context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putFloat("rotationY", rotationY)
            putFloat("scale", scale)
            putString("wallColor", wallColor)
            putString("roofColor", roofColor)
            putBoolean("windowShapeSquare", windowShapeSquare)
            putString("doorColor", doorColor)
            apply()
        }
    }

    fun loadFromPreferences(context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        rotationY = prefs.getFloat("rotationY", 0f)
        scale = prefs.getFloat("scale", 1f)
        wallColor = prefs.getString("wallColor", "WHITE") ?: "WHITE"
        roofColor = prefs.getString("roofColor", "RED") ?: "RED"
        windowShapeSquare = prefs.getBoolean("windowShapeSquare", true)
        doorColor = prefs.getString("doorColor", "BROWN") ?: "BROWN"
    }
}

enum class Element {
    WALLS, ROOF, WINDOWS, DOOR
}
