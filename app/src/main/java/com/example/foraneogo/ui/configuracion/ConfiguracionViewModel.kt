package com.example.foraneogo.ui.configuracion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foraneogo.data.model.Sector
import com.example.foraneogo.data.repository.SectorRepository
import com.example.foraneogo.data.repository.PreferenciasRepository
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
import kotlinx.coroutines.launch

class ConfiguracionViewModel(
    sectorRepository: SectorRepository,
    private val preferenciasRepository: PreferenciasRepository
) : ViewModel() {

    // Eventos puntuales (mensajes), separados del estado de la pantalla.
    private val canalEventos = Channel<UiEvent>(Channel.BUFFERED)
    val eventos: Flow<UiEvent> = canalEventos.receiveAsFlow()

    // Borrador del precio que el usuario está escribiendo.
    // null = no ha editado nada: se muestra el precio ya guardado.
    private val borradorPrecio = MutableStateFlow<String?>(null)

    // Único estado que expone este ViewModel. Junta sectores, preferencias y borrador.
    val estado: StateFlow<ConfiguracionUiState> = combine<
            List<Sector>, Long?, Double, Boolean, String?, ConfiguracionUiState
            >(
        sectorRepository.observarSectores(),
        preferenciasRepository.observarSectorPreferido(),
        preferenciasRepository.observarPrecioMaximo(),
        preferenciasRepository.observarModoOscuro(),
        borradorPrecio
    ) { sectores, sectorPreferido, precioMaximo, modoOscuro, borrador ->
        ConfiguracionUiState.Success(
            sectores = sectores,
            sectorPreferidoId = sectorPreferido,
            precioMaximo = precioMaximo,
            precioTexto = borrador ?: precioMaximo.aTexto(),
            modoOscuro = modoOscuro
        )
    }
        .catch {
            emit(ConfiguracionUiState.Error("No se pudo cargar la configuración"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ConfiguracionUiState.Loading
        )

    // Acciones del usuario.

    // La pantalla avisa cada cambio del campo de precio; el ViewModel guarda el borrador.
    fun cambiarPrecioTexto(texto: String) {
        borradorPrecio.value = texto
    }

    // Valida el borrador y lo guarda. Rechaza vacío, no numérico, 0 o negativo.
    fun guardarPrecioMaximo() {
        val texto = borradorPrecio.value
        if (texto == null) {
            enviarMensaje("No hay cambios en el precio")
            return
        }
        val precio = texto.replace(',', '.').toDoubleOrNull()
        if (precio == null || precio <= 0.0) {
            enviarMensaje("Ingresa un precio mayor a 0")
            return
        }
        ejecutar("Precio máximo guardado") {
            preferenciasRepository.guardarPrecioMaximo(precio)
            borradorPrecio.value = null
        }
    }

    // sectorId = null significa "sin sector preferido".
    fun guardarSectorPreferido(sectorId: Long?) {
        ejecutar("Sector preferido guardado") {
            preferenciasRepository.guardarSectorPreferido(sectorId)
        }
    }

    fun guardarModoOscuro(activado: Boolean) {
        ejecutar(mensajeExito = null) {
            preferenciasRepository.guardarModoOscuro(activado)
        }
    }

    // 2000.0 se muestra como "2000"; 150.5 se queda como "150.5".
    private fun Double.aTexto(): String =
        if (this % 1.0 == 0.0) toLong().toString() else toString()

    private fun enviarMensaje(mensaje: String) {
        viewModelScope.launch {
            canalEventos.send(UiEvent.MostrarMensaje(mensaje))
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