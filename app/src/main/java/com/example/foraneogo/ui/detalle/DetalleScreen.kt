package com.example.foraneogo.ui.detalle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foraneogo.ui.common.ViewModelFactory

@Composable
fun DetalleScreen(
    alojamientoId: Long,
    onVolver: () -> Unit,
    viewModel: DetalleViewModel = viewModel(factory = ViewModelFactory.Factory)
) {
    val estado by viewModel.estado.collectAsState()

    DetalleContent(
        estado = estado,
        onVolver = onVolver
    )
}
