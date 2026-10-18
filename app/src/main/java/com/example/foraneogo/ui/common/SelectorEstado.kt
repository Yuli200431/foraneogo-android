package com.example.foraneogo.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.foraneogo.data.local.entities.EstadoSeguimiento

// Texto legible de cada estado, para mostrar en cualquier pantalla.
fun EstadoSeguimiento.textoLegible(): String = when (this) {
    EstadoSeguimiento.PENDIENTE -> "Pendiente"
    EstadoSeguimiento.CONTACTADO -> "Contactado"
    EstadoSeguimiento.PENDIENTE_VISITA -> "Pendiente de visita"
    EstadoSeguimiento.DESCARTADO -> "Descartado"
}

// Selector de los 4 estados. No guarda nada: recibe el seleccionado y avisa el cambio.
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectorEstado(
    seleccionado: EstadoSeguimiento,
    onSeleccionar: (EstadoSeguimiento) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        EstadoSeguimiento.entries.forEach { estado ->
            FilterChip(
                selected = estado == seleccionado,
                onClick = { onSeleccionar(estado) },
                label = { Text(estado.textoLegible()) }
            )
        }
    }
}