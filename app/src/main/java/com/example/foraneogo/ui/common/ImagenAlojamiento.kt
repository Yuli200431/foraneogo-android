package com.example.foraneogo.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.foraneogo.R

@Composable
fun ImagenAlojamiento(
    nombreImagen: String?,
    descripcion: String,
    modifier: Modifier = Modifier
) {
    val recurso = remember(nombreImagen) {
        val lower = nombreImagen?.lowercase() ?: ""
        when {
            lower.contains("1") -> R.drawable.alojamiento_1
            lower.contains("2") -> R.drawable.alojamiento_2
            lower.contains("3") -> R.drawable.alojamiento_3
            lower.contains("4") -> R.drawable.alojamiento_4
            lower.contains("5") -> R.drawable.alojamiento_5
            lower.contains("6") -> R.drawable.alojamiento_6
            else -> 0
        }
    }

    if (recurso != 0) {
        Image(
            painter = painterResource(recurso),
            contentDescription = descripcion,
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Sin imagen",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
