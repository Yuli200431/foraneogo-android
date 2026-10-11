package com.example.foraneogo.ui.dashboard

sealed interface DashboardUiState {
    data object Loading : DashboardUiState

    data class Success(
        val totalAlojamientos: Int,
        val totalBitacora: Int,
        val totalFavoritos: Int
    ) : DashboardUiState

    data class Error(val mensaje: String) : DashboardUiState
}