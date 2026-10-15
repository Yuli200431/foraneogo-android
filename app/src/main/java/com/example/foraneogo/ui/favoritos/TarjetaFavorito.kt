package com.example.foraneogo.ui.favoritos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.foraneogo.data.model.Alojamiento
import com.example.foraneogo.ui.common.ImagenAlojamiento

// Pieza visual pura: dibuja un favorito y avisa lo que el usuario toca.
@Composable
fun TarjetaFavorito(
    alojamiento: Alojamiento,
    onClick: () -> Unit,
    onQuitar: () -> Unit,
    modifier: Modifier = Modifier
) {
    val precio = "%.2f".format(alojamiento.precioMensual)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ImagenAlojamiento(
                nombreImagen = alojamiento.imagen,
                descripcion = alojamiento.titulo,
                modifier = Modifier.size(88.dp)
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = alojamiento.titulo,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "$$precio / mes",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = alojamiento.sectorNombre,
                    style = MaterialTheme.typography.bodySmall
                )
                OutlinedButton(onClick = onQuitar) {
                    Text("Quitar")
                }
            }
        }
    }
}