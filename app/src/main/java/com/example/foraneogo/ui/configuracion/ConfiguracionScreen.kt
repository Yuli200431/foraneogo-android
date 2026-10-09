package com.example.foraneogo.ui.configuracion

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foraneogo.ui.common.ViewModelFactory

@Composable
fun ConfiguracionScreen(
    onVolver: () -> Unit,
    viewModel: ConfiguracionViewModel = viewModel(factory = ViewModelFactory.Factory)
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()

    ConfiguracionContent(
        estado = estado,
        onVolver = onVolver
    )
}
