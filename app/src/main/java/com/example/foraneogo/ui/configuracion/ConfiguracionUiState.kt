package com.example.foraneogo.ui.configuracion


import com.example.foraneogo.data.model.Sector

// Exactamente lo que necesita la pantalla de Configuración, nada más.
data class ConfiguracionUiState(
    val cargando: Boolean = true,
    val sectores: List<Sector> = emptyList(),
    val sectorPreferidoId: Long? = null,
    val precioMaximo: Double = 2000.0,
    val modoOscuro: Boolean = false,
    val error: String? = null
)