package com.example.foraneogo.ui.favoritos

import com.example.foraneogo.data.model.Alojamiento

// Exactamente lo que necesita la pantalla de Favoritos, nada más.
sealed interface FavoritosUiState {
    data object Loading : FavoritosUiState

    data class Success(val favoritos: List<Alojamiento>) : FavoritosUiState {
        val estaVacia: Boolean get() = favoritos.isEmpty()
    }

    data class Error(val mensaje: String) : FavoritosUiState
}