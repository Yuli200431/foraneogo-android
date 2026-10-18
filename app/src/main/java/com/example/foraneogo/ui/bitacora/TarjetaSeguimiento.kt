package com.example.foraneogo.ui.bitacora

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.foraneogo.data.local.entities.EstadoSeguimiento
import com.example.foraneogo.data.model.SeguimientoConAlojamiento
import com.example.foraneogo.ui.common.SelectorEstado
import com.example.foraneogo.ui.common.formatearFecha

// Pieza visual pura: dibuja un registro de la bitácora y avisa lo que el usuario toca.
@Composable
fun TarjetaSeguimiento(
    item: SeguimientoConAlojamiento,
    onCambiarEstado: (EstadoSeguimiento) -> Unit,
    onEliminar: () -> Unit,
    modifier: Modifier = Modifier
) {
    val seguimiento = item.seguimiento
    val alojamiento = item.alojamiento

    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = alojamiento.titulo,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = alojamiento.sectorNombre,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Registrado: ${formatearFecha(seguimiento.fechaCreacionMillis)}",
                style = MaterialTheme.typography.bodySmall
            )
            if (seguimiento.fechaCitaMillis != null) {
                Text(
                    text = "Cita: ${formatearFecha(seguimiento.fechaCitaMillis)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            if (seguimiento.notas.isNotBlank()) {
                Text(
                    text = "Notas: ${seguimiento.notas}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            SelectorEstado(
                seleccionado = seguimiento.estado,
                onSeleccionar = onCambiarEstado
            )

            OutlinedButton(onClick = onEliminar) {
                Text("Eliminar")
            }
        }
    }
}