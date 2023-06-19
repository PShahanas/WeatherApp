package com.example.weatherapps.ui.Weather

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.Dp
import androidx.compose.foundation.Image

@Composable
fun ColoredImageVector(
    imageVector: ImageVector,
    color: Color,
    contentDescription: String?,
    size: Dp
) {
    Image(
        imageVector = imageVector,
        contentDescription = contentDescription,
        colorFilter = ColorFilter.tint(color),
        modifier = Modifier.size(size)
    )
}