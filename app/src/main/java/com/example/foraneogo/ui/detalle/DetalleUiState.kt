package com.example.foraneogo.ui.detalle

import com.example.foraneogo.data.local.entities.EstadoSeguimiento
import com.example.foraneogo.data.model.Alojamiento
import com.example.foraneogo.data.model.Seguimiento

// Exactamente lo que necesita la pantalla de Detalle, nada más.
sealed interface DetalleUiState {
    data object Loading : DetalleUiState

    data class Success(
        val alojamiento: Alojamiento,
        val seguimiento: Seguimiento?,
        // Borrador del formulario (si no se editó, trae los valores guardados)
        val estadoElegido: EstadoSeguimiento,
        val notas: String,
        val fechaCitaMillis: Long?
    ) : DetalleUiState {
        val esFavorito: Boolean get() = alojamiento.esFavorito
        val tieneSeguimiento: Boolean get() = seguimiento != null
    }

    data class Error(val mensaje: String) : DetalleUiState
}