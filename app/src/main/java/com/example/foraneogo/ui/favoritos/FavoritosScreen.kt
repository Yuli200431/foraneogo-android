package com.example.foraneogo.ui.favoritos

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foraneogo.ui.common.ObservarEventos
import com.example.foraneogo.ui.common.ViewModelFactory

@Composable
fun FavoritosScreen(
    onAlojamientoClick: (Long) -> Unit,
    onVolver: () -> Unit,
    viewModel: FavoritosViewModel = viewModel(factory = ViewModelFactory.Factory)
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    ObservarEventos(viewModel.eventos, snackbarHostState)

    FavoritosContent(
        estado = estado,
        snackbarHostState = snackbarHostState,
        onAlojamientoClick = onAlojamientoClick,
        onQuitar = viewModel::alternarFavorito,
        onVolver = onVolver
    )
}