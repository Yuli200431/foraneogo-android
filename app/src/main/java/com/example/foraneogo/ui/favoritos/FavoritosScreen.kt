package com.example.foraneogo.ui.favoritos

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foraneogo.ui.common.ObservarEventos

@Composable
fun FavoritosScreen(
    onAlojamientoClick: (Long) -> Unit,
    onVolver: () -> Unit,
    viewModel: FavoritosViewModel = hiltViewModel()
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