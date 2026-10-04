package com.example.foraneogo.ui.detalle


import com.example.foraneogo.data.model.Alojamiento
import com.example.foraneogo.data.model.Seguimiento

// Exactamente lo que necesita la pantalla de Detalle, nada más.
data class DetalleUiState(
    val cargando: Boolean = true,
    val alojamiento: Alojamiento? = null,
    val seguimiento: Seguimiento? = null,
    val error: String? = null
) {
    val esFavorito: Boolean get() = alojamiento?.esFavorito == true
    val tieneSeguimiento: Boolean get() = seguimiento != null
}