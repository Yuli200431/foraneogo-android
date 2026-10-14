package com.example.foraneogo.ui.detalle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.foraneogo.data.local.entities.EstadoSeguimiento
import com.example.foraneogo.ui.common.SelectorEstado
import com.example.foraneogo.ui.common.formatearFecha

// Pieza visual pura: no guarda nada. Recibe los datos del borrador y avisa los cambios.
@Composable
fun FormularioSeguimiento(
    estadoElegido: EstadoSeguimiento,
    notas: String,
    fechaCitaMillis: Long?,
    tieneSeguimiento: Boolean,
    onCambiarEstado: (EstadoSeguimiento) -> Unit,
    onCambiarNotas: (String) -> Unit,
    onGuardar: () -> Unit,
    onEliminar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Mi seguimiento",
            style = MaterialTheme.typography.titleMedium
        )

        SelectorEstado(
            seleccionado = estadoElegido,
            onSeleccionar = onCambiarEstado
        )

        OutlinedTextField(
            value = notas,
            onValueChange = onCambiarNotas,
            label = { Text("Notas") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = if (fechaCitaMillis != null) {
                "Fecha de cita: ${formatearFecha(fechaCitaMillis)}"
            } else {
                "Sin fecha de cita"
            },
            style = MaterialTheme.typography.bodyMedium
        )

        Button(onClick = onGuardar, modifier = Modifier.fillMaxWidth()) {
            Text("Guardar seguimiento")
        }

        if (tieneSeguimiento) {
            OutlinedButton(onClick = onEliminar, modifier = Modifier.fillMaxWidth()) {
                Text("Eliminar seguimiento")
            }
        }
    }
}