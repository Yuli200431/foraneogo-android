package com.example.foraneogo.ui.dashboard

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DashboardScreen(
    onNavegarACatalogo: () -> Unit,
    onNavegarABitacora: () -> Unit,
    onNavegarAFavoritos: () -> Unit,
    onNavegarAConfiguracion: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()

    DashboardContent(
        estado = estado,
        onNavegarACatalogo = onNavegarACatalogo,
        onNavegarABitacora = onNavegarABitacora,
        onNavegarAFavoritos = onNavegarAFavoritos,
        onNavegarAConfiguracion = onNavegarAConfiguracion
    )
}
