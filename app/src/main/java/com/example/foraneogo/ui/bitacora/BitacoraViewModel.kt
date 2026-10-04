package com.example.foraneogo.ui.bitacora

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foraneogo.data.local.entities.EstadoSeguimiento
import com.example.foraneogo.data.repository.SeguimientoRepository
import com.example.foraneogo.ui.common.UiEvent
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BitacoraViewModel(
    private val seguimientoRepository: SeguimientoRepository
) : ViewModel() {

    // Eventos puntuales (mensajes), separados del estado de la pantalla.
    private val canalEventos = Channel<UiEvent>(Channel.BUFFERED)
    val eventos: Flow<UiEvent> = canalEventos.receiveAsFlow()

    // Único estado que expone este ViewModel.
    val estado: StateFlow<BitacoraUiState> = seguimientoRepository.observarBitacora()
        .map { lista ->
            BitacoraUiState(cargando = false, seguimientos = lista)
        }
        .catch {
            emit(BitacoraUiState(cargando = false, error = "No se pudo cargar la bitácora"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = BitacoraUiState()
        )

    // Acciones del usuario.
    fun actualizarSeguimiento(
        alojamientoId: Long,
        estado: EstadoSeguimiento,
        notas: String,
        fechaCitaMillis: Long?
    ) {
        ejecutar("Seguimiento actualizado") {
            seguimientoRepository.guardarSeguimiento(
                alojamientoId = alojamientoId,
                estado = estado,
                notas = notas,
                fechaCitaMillis = fechaCitaMillis
            )
        }
    }

    fun eliminarSeguimiento(alojamientoId: Long) {
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