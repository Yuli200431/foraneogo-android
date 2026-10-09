package com.example.foraneogo.ui.catalogo

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foraneogo.ui.common.ViewModelFactory

@Composable
fun CatalogoScreen(
    onAlojamientoClick: (Long) -> Unit,
    onVolver: () -> Unit,
    viewModel: CatalogoViewModel = viewModel(factory = ViewModelFactory.Factory)
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()

    CatalogoContent(
        estado = estado,
        onAlojamientoClick = onAlojamientoClick,
        onVolver = onVolver
    )
}
