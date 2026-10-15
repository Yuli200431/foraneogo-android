package com.example.foraneogo.ui.bitacora

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foraneogo.ui.common.ObservarEventos
import com.example.foraneogo.ui.common.ViewModelFactory

@Composable
fun BitacoraScreen(
    onVolver: () -> Unit,
    viewModel: BitacoraViewModel = viewModel(factory = ViewModelFactory.Factory)
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    ObservarEventos(viewModel.eventos, snackbarHostState)

    BitacoraContent(
        estado = estado,
        snackbarHostState = snackbarHostState,
        onVolver = onVolver,
        onCambiarEstado = { item, nuevoEstado ->
            // Conserva las notas y la fecha de cita que ya tenía el registro.
            viewModel.actualizarSeguimiento(
                alojamientoId = item.alojamiento.id,
                estado = nuevoEstado,
                notas = item.seguimiento.notas,
                fechaCitaMillis = item.seguimiento.fechaCitaMillis
            )
        },
        onEliminar = viewModel::eliminarSeguimiento
    )
}