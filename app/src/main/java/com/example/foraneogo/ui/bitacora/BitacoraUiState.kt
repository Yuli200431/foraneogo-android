package com.example.foraneogo.ui.bitacora

import com.example.foraneogo.data.model.SeguimientoConAlojamiento

// Exactamente lo que necesita la pantalla de la Bitácora, nada más.
sealed interface BitacoraUiState {
    data object Loading : BitacoraUiState

    data class Success(val seguimientos: List<SeguimientoConAlojamiento>) : BitacoraUiState {
        val estaVacia: Boolean get() = seguimientos.isEmpty()
    }

    data class Error(val mensaje: String) : BitacoraUiState
}