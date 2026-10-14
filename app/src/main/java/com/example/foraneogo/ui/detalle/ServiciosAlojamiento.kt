package com.example.foraneogo.ui.detalle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.foraneogo.data.model.Alojamiento

// Pieza visual pura: recibe el alojamiento y dibuja qué servicios incluye.
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ServiciosAlojamiento(
    alojamiento: Alojamiento,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Servicio("WiFi", alojamiento.wifi)
        Servicio("Agua", alojamiento.agua)
        Servicio("Luz", alojamiento.luz)
        Servicio("Lavandería", alojamiento.lavanderia)
        Servicio("Amoblado", alojamiento.amoblado)
    }
}

@Composable
private fun Servicio(nombre: String, incluido: Boolean) {
    Text(
        text = if (incluido) "✓ $nombre" else "✗ $nombre",
        style = MaterialTheme.typography.bodyMedium,
        color = if (incluido) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        }
    )
}