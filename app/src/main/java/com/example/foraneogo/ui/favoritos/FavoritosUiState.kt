package com.example.foraneogo.ui.favoritos

import com.example.foraneogo.data.model.Alojamiento

//Exactamente lo que necesita la pantalla de Favoritos, nada más.
data class FavoritosUiState(
    val cargando: Boolean = true,
    val favoritos: List<Alojamiento> = emptyList(),
    val error: String? = null
) {
    val estaVacia: Boolean get() = !cargando && error == null && favoritos.isEmpty()
}