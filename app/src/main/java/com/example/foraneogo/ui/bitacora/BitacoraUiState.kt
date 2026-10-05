package com.example.foraneogo.ui.bitacora


import com.example.foraneogo.data.model.SeguimientoConAlojamiento

// Exactamente lo que necesita la pantalla de la Bitácora, nada más.
data class BitacoraUiState(
    val cargando: Boolean = true,
    val seguimientos: List<SeguimientoConAlojamiento> = emptyList(),
    val error: String? = null
) {
    val estaVacia: Boolean get() = !cargando && error == null && seguimientos.isEmpty()
}