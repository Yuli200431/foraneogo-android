package com.example.foraneogo.ui.catalogo

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CatalogoScreen(
    onAlojamientoClick: (Long) -> Unit,
    onVolver: () -> Unit,
    viewModel: CatalogoViewModel = hiltViewModel()
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()

    CatalogoContent(
        estado = estado,
        onAlojamientoClick = onAlojamientoClick,
        onVolver = onVolver,
        onBuscarCambio = viewModel::cambiarTexto
    )
}
