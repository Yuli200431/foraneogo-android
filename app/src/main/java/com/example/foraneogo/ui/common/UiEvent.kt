package com.example.foraneogo.ui.common

// Eventos puntuales: se consumen una sola vez y NO forman parte del estado de la pantalla.
sealed interface UiEvent {
    data class MostrarMensaje(val mensaje: String) : UiEvent
}