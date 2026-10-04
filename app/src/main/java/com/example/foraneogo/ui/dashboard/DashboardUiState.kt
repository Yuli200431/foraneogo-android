package com.example.foraneogo.ui.dashboard

data class DashboardUiState(
    val cargando: Boolean = true,
    val totalAlojamientos: Int = 0,
    val totalBitacora: Int = 0,
    val totalFavoritos: Int = 0,
    val error: String? = null
)