package com.example.foraneogo.ui.detalle

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foraneogo.data.local.entities.EstadoSeguimiento
import com.example.foraneogo.data.model.Alojamiento
import com.example.foraneogo.data.model.Seguimiento
import com.example.foraneogo.data.repository.CatalogoRepository
import com.example.foraneogo.data.repository.SeguimientoRepository
import com.example.foraneogo.ui.common.UiEvent
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetalleViewModel(
    savedStateHandle: SavedStateHandle,
    catalogoRepository: CatalogoRepository,
    private val seguimientoRepository: SeguimientoRepository
) : ViewModel() {

    companion object {
        // Nombre del argumento de navegación. Se usa en la ruta y en NavGraph.
        const val ARG_ALOJAMIENTO_ID = "alojamientoId"
    }

    // El id llega por la navegación desde el catálogo.
    private val alojamientoId: Long = savedStateHandle[ARG_ALOJAMIENTO_ID] ?: -1L

    // Eventos puntuales (mensajes), separados del estado de la pantalla.
    private val canalEventos = Channel<UiEvent>(Channel.BUFFERED)
    val eventos: Flow<UiEvent> = canalEventos.receiveAsFlow()

    // Borrador del formulario. Cada campo en null = "no se ha editado".
    private data class Borrador(
        val estado: EstadoSeguimiento? = null,
        val notas: String? = null,
        val fechaCitaMillis: Long? = null,
        val fechaEditada: Boolean = false
    )

    private val borrador = MutableStateFlow(Borrador())

    // Único estado que expone este ViewModel.
    val estado: StateFlow<DetalleUiState> = combine<Alojamiento?, Seguimiento?, Borrador, DetalleUiState>(
        catalogoRepository.observarAlojamiento(alojamientoId),
        seguimientoRepository.observarSeguimiento(alojamientoId),
        borrador
    ) { alojamiento, seguimiento, b ->
        if (alojamiento == null) {
            DetalleUiState.Error("No se encontró el alojamiento")
        } else {
            DetalleUiState.Success(
                alojamiento = alojamiento,
                seguimiento = seguimiento,
                estadoElegido = b.estado ?: seguimiento?.estado ?: EstadoSeguimiento.PENDIENTE,
                notas = b.notas ?: seguimiento?.notas ?: "",
                fechaCitaMillis = if (b.fechaEditada) b.fechaCitaMillis else seguimiento?.fechaCitaMillis
            )
        }
    }
        .catch {
            emit(DetalleUiState.Error("No se pudo cargar el alojamiento"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DetalleUiState.Loading
        )

    // Cambios del formulario: la pantalla solo avisa, el borrador vive aquí.
    fun cambiarEstado(nuevoEstado: EstadoSeguimiento) {
        borrador.update { it.copy(estado = nuevoEstado) }
    }

    fun cambiarNotas(texto: String) {
        borrador.update { it.copy(notas = texto) }
    }

    // null = quitar la fecha de cita
    fun cambiarFecha(fechaMillis: Long?) {
        borrador.update { it.copy(fechaCitaMillis = fechaMillis, fechaEditada = true) }
    }

    // Guarda lo que muestra el formulario y limpia el borrador.
    fun guardar() {
        val actual = estado.value as? DetalleUiState.Success ?: return
        ejecutar("Seguimiento guardado") {
            seguimientoRepository.guardarSeguimiento(
                alojamientoId = alojamientoId,
                estado = actual.estadoElegido,
                notas = actual.notas,
                fechaCitaMillis = actual.fechaCitaMillis
            )
            borrador.value = Borrador()
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
            borrador.value = Borrador()
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