package com.example.foraneogo.ui.configuracion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foraneogo.ui.common.ViewModelFactory

@Composable
fun ConfiguracionScreen(
    onVolver: () -> Unit,
    viewModel: ConfiguracionViewModel = viewModel(factory = ViewModelFactory.Factory)
) {
    val estado by viewModel.estado.collectAsState()

    ConfiguracionContent(
        estado = estado,
        onVolver = onVolver
    )
}
