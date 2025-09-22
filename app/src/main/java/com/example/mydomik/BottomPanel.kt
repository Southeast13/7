package com.example.mydomik

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun BottomPanel(vm: HouseViewModel, modifier: Modifier = Modifier) {
    Row(modifier = modifier.height(92.dp), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
        PanelButton(text = "🎨\nЦвета", onClick = { /* open color chooser - simple demo */ vm.wallColor = "YELLOW" })
        PanelButton(text = "🏠\nСтены", onClick = { vm.selected = Element.WALLS })
        PanelButton(text = "🪟\nОкна", onClick = { vm.selected = Element.WINDOWS })
        PanelButton(text = "🚪\nДверь", onClick = { vm.selected = Element.DOOR })
    }
}

@Composable
fun PanelButton(text: String, onClick: () -> Unit) {
    Surface(shape = RoundedCornerShape(12.dp), elevation = 4.dp, modifier = Modifier.size(64.dp).clickable { onClick() }) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = text, modifier = Modifier.padding(4.dp), color = Color.Black)
        }
    }
}
