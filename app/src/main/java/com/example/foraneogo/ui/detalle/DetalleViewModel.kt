package com.example.foraneogo.ui.detalle

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foraneogo.data.local.entities.EstadoSeguimiento
import com.example.foraneogo.data.repository.CatalogoRepository
import com.example.foraneogo.data.repository.SeguimientoRepository
import com.example.foraneogo.ui.common.UiEvent
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetalleViewModel(
    savedStateHandle: SavedStateHandle,
    catalogoRepository: CatalogoRepository,
    private val seguimientoRepository: SeguimientoRepository
) : ViewModel() {

    companion object {
        // Nombre del argumento de navegación. Bryan debe usar este mismo nombre en la ruta.
        const val ARG_ALOJAMIENTO_ID = "alojamientoId"
    }

    // El id llega por la navegación desde el catálogo.
    private val alojamientoId: Long = savedStateHandle[ARG_ALOJAMIENTO_ID] ?: -1L

    // Eventos puntuales (mensajes), separados del estado de la pantalla.
    private val canalEventos = Channel<UiEvent>(Channel.BUFFERED)
    val eventos: Flow<UiEvent> = canalEventos.receiveAsFlow()

    // Único estado que expone este ViewModel.
    val estado: StateFlow<DetalleUiState> = combine(
        catalogoRepository.observarAlojamiento(alojamientoId),
        seguimientoRepository.observarSeguimiento(alojamientoId)
    ) { alojamiento, seguimiento ->
        if (alojamiento == null) {
            DetalleUiState(cargando = false, error = "No se encontró el alojamiento")
        } else {
            DetalleUiState(
                cargando = false,
                alojamiento = alojamiento,
                seguimiento = seguimiento
            )
        }
    }
        .catch {
            emit(DetalleUiState(cargando = false, error = "No se pudo cargar el alojamiento"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DetalleUiState()
        )

    // Acciones del usuario.
    fun guardarSeguimiento(
        estado: EstadoSeguimiento,
        notas: String,
        fechaCitaMillis: Long?
    ) {
        ejecutar("Seguimiento guardado") {
            seguimientoRepository.guardarSeguimiento(
                alojamientoId = alojamientoId,
                estado = estado,
                notas = notas,
                fechaCitaMillis = fechaCitaMillis
            )
        }
    }

    fun alternarFavorito() {
        ejecutar("Favorito actualizado") {
            seguimientoRepository.alternarFavorito(alojamientoId)
        }
    }

    fun eliminarSeguimiento() {
        ejecutar("Seguimiento eliminado") {
            seguimientoRepository.eliminarSeguimiento(alojamientoId)
        }
    }

    // Ejecuta una acción y avisa el resultado con un evento puntual.
    private fun ejecutar(mensajeExito: String, accion: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                accion()
                canalEventos.send(UiEvent.MostrarMensaje(mensajeExito))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                canalEventos.send(UiEvent.MostrarMensaje("No se pudo completar la acción"))
            }
        }
    }
}