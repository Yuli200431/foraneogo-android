package com.example.foraneogo.ui.configuracion

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foraneogo.ui.common.ObservarEventos

@Composable
fun ConfiguracionScreen(
    onVolver: () -> Unit,
    viewModel: ConfiguracionViewModel = hiltViewModel()
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    ObservarEventos(viewModel.eventos, snackbarHostState)

    ConfiguracionContent(
        estado = estado,
        snackbarHostState = snackbarHostState,
        onVolver = onVolver,
        onCambiarPrecio = viewModel::cambiarPrecioTexto,
        onGuardarPrecio = viewModel::guardarPrecioMaximo,
        onGuardarSector = viewModel::guardarSectorPreferido,
        onGuardarModoOscuro = viewModel::guardarModoOscuro
    )
}
