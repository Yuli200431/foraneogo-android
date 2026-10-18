package com.example.foraneogo.ui.configuracion

import com.example.foraneogo.data.model.Sector

// Exactamente lo que necesita la pantalla de Configuración, nada más.
sealed interface ConfiguracionUiState {
    data object Loading : ConfiguracionUiState

    data class Success(
        val sectores: List<Sector>,
        val sectorPreferidoId: Long?,
        val precioMaximo: Double,
        val precioTexto: String, // lo que se ve en el campo (borrador o precio guardado)
        val modoOscuro: Boolean
    ) : ConfiguracionUiState

    data class Error(val mensaje: String) : ConfiguracionUiState
}