package com.example.foraneogo.ui.catalogo


import com.example.foraneogo.data.model.Alojamiento
import com.example.foraneogo.data.model.Sector

// Exactamente lo que necesita la pantalla del Catálogo, nada más.
data class CatalogoUiState(
    val cargando: Boolean = true,
    val alojamientos: List<Alojamiento> = emptyList(),
    val sectores: List<Sector> = emptyList(),
    val sectorSeleccionadoId: Long? = null,
    val precioMaximo: Double = 2000.0,
    val textoBusqueda: String = "",
    val error: String? = null
)