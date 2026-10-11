package com.example.foraneogo.ui.common

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow

// Escucha los eventos puntuales de un ViewModel y los muestra como Snackbar.
// Se llama desde el Screen (el que tiene el ViewModel), no desde el Content.
@Composable
fun ObservarEventos(
    eventos: Flow<UiEvent>,
    snackbarHostState: SnackbarHostState
) {
    LaunchedEffect(eventos) {
        eventos.collect { evento ->
            when (evento) {
                is UiEvent.MostrarMensaje -> snackbarHostState.showSnackbar(evento.mensaje)
            }
        }
    }
}