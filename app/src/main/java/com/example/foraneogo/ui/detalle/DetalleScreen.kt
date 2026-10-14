package com.example.foraneogo.ui.detalle

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foraneogo.ui.common.ObservarEventos
import com.example.foraneogo.ui.common.ViewModelFactory

@Composable
fun DetalleScreen(
    onVolver: () -> Unit,
    viewModel: DetalleViewModel = viewModel(factory = ViewModelFactory.Factory)
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val contexto = LocalContext.current

    // Muestra los mensajes del ViewModel ("Seguimiento guardado", etc.) como Snackbar.
    ObservarEventos(viewModel.eventos, snackbarHostState)

    DetalleContent(
        estado = estado,
        snackbarHostState = snackbarHostState,
        onVolver = onVolver,
        onAlternarFavorito = viewModel::alternarFavorito,
        onCambiarEstado = viewModel::cambiarEstado,
        onCambiarNotas = viewModel::cambiarNotas,
        onGuardar = viewModel::guardar,
        onEliminar = viewModel::eliminarSeguimiento,
        onContactarWhatsApp = { numero -> abrirWhatsApp(contexto, numero) },
        onLlamar = { numero -> abrirMarcador(contexto, numero) }
    )
}