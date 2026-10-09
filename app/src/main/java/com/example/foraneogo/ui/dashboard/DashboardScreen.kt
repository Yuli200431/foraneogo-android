package com.example.foraneogo.ui.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foraneogo.ui.common.ViewModelFactory

@Composable
fun DashboardScreen(
    onNavegarACatalogo: () -> Unit,
    onNavegarABitacora: () -> Unit,
    onNavegarAFavoritos: () -> Unit,
    onNavegarAConfiguracion: () -> Unit,
    viewModel: DashboardViewModel = viewModel(factory = ViewModelFactory.Factory)
) {
    val estado by viewModel.estado.collectAsState()

    DashboardContent(
        estado = estado,
        onNavegarACatalogo = onNavegarACatalogo,
        onNavegarABitacora = onNavegarABitacora,
        onNavegarAFavoritos = onNavegarAFavoritos,
        onNavegarAConfiguracion = onNavegarAConfiguracion
    )
}
