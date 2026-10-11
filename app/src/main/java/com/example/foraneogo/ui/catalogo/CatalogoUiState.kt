package com.example.foraneogo.ui.catalogo

import com.example.foraneogo.data.model.Alojamiento
import com.example.foraneogo.data.model.Sector

// Exactamente lo que necesita la pantalla del Catálogo, nada más.
sealed interface CatalogoUiState {
    data object Loading : CatalogoUiState

    data class Success(
        val alojamientos: List<Alojamiento>,
        val sectores: List<Sector>,
        val sectorSeleccionadoId: Long?,
        val precioMaximo: Double,
        val textoBusqueda: String
    ) : CatalogoUiState {
        val estaVacia: Boolean get() = alojamientos.isEmpty()
    }

    data class Error(val mensaje: String) : CatalogoUiState
}