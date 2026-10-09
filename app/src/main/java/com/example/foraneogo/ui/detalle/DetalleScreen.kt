package com.example.foraneogo.ui.detalle

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foraneogo.ui.common.ViewModelFactory

@Composable
fun DetalleScreen(
    onVolver: () -> Unit,
    viewModel: DetalleViewModel = viewModel(factory = ViewModelFactory.Factory)
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()

    DetalleContent(
        estado = estado,
        onVolver = onVolver
    )
}
