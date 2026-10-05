package com.example.foraneogo.ui.configuracion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foraneogo.data.repository.CatalogoRepository
import com.example.foraneogo.data.repository.PreferenciasRepository
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

class ConfiguracionViewModel(
    catalogoRepository: CatalogoRepository,
    private val preferenciasRepository: PreferenciasRepository
) : ViewModel() {

    // Eventos puntuales (mensajes), separados del estado de la pantalla.
    private val canalEventos = Channel<UiEvent>(Channel.BUFFERED)
    val eventos: Flow<UiEvent> = canalEventos.receiveAsFlow()

    // Único estado que expone este ViewModel. Junta sectores y preferencias guardadas.
    val estado: StateFlow<ConfiguracionUiState> = combine(
        catalogoRepository.observarSectores(),
        preferenciasRepository.observarSectorPreferido(),
        preferenciasRepository.observarPrecioMaximo(),
        preferenciasRepository.observarModoOscuro()
    ) { sectores, sectorPreferido, precioMaximo, modoOscuro ->
        ConfiguracionUiState(
            cargando = false,
            sectores = sectores,
            sectorPreferidoId = sectorPreferido,
            precioMaximo = precioMaximo,
            modoOscuro = modoOscuro
        )
    }
        .catch {
            emit(ConfiguracionUiState(cargando = false, error = "No se pudo cargar la configuración"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ConfiguracionUiState()
        )

    // Acciones del usuario. sectorId = null significa "sin sector preferido".
    fun guardarSectorPreferido(sectorId: Long?) {
        ejecutar("Sector preferido guardado") {
            preferenciasRepository.guardarSectorPreferido(sectorId)
        }
    }

    fun guardarPrecioMaximo(precio: Double) {
        if (precio <= 0.0) {
            viewModelScope.launch {
                canalEventos.send(UiEvent.MostrarMensaje("El precio debe ser mayor a 0"))
            }
            return
        }
        ejecutar("Precio máximo guardado") {
            preferenciasRepository.guardarPrecioMaximo(precio)
        }
    }

    fun guardarModoOscuro(activado: Boolean) {
        ejecutar(mensajeExito = null) {
            preferenciasRepository.guardarModoOscuro(activado)
        }
    }

    // Ejecuta una acción y avisa el resultado con un evento puntual.
    // Si mensajeExito es null, solo avisa cuando algo falla.
    private fun ejecutar(mensajeExito: String?, accion: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                accion()
                if (mensajeExito != null) {
                    canalEventos.send(UiEvent.MostrarMensaje(mensajeExito))
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                canalEventos.send(UiEvent.MostrarMensaje("No se pudo guardar la preferencia"))
            }
        }
    }
}