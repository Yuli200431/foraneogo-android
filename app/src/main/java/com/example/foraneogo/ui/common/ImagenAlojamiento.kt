package com.example.foraneogo.ui.common

import android.annotation.SuppressLint
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

// Vista pura: recibe solo el nombre de la imagen (el texto que está en el
// campo `imagen` del alojamiento) y no conoce ningún ViewModel.
// Si el nombre es null o no existe en res/drawable-nodpi, muestra un marcador.
@SuppressLint("DiscouragedApi")
@Composable
fun ImagenAlojamiento(
    nombreImagen: String?,
    descripcion: String,
    modifier: Modifier = Modifier
) {
    val contexto = LocalContext.current

    // Busca el recurso por su nombre. Es un dato visual, no de negocio.
    val recurso = remember(nombreImagen) {
        if (nombreImagen.isNullOrBlank()) {
            0
        } else {
            contexto.resources.getIdentifier(nombreImagen, "drawable", contexto.packageName)
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