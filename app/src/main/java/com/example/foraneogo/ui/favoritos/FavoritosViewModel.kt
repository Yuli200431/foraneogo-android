package com.example.foraneogo.ui.favoritos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import com.example.foraneogo.data.model.Alojamiento
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritosViewModel @Inject constructor(
    private val seguimientoRepository: SeguimientoRepository
) : ViewModel() {

    // Eventos puntuales (mensajes), separados del estado de la pantalla.
    private val canalEventos = Channel<UiEvent>(Channel.BUFFERED)
    val eventos: Flow<UiEvent> = canalEventos.receiveAsFlow()

    // Único estado que expone este ViewModel.
    val estado: StateFlow<FavoritosUiState> = seguimientoRepository.observarFavoritos()
        .map<List<Alojamiento>, FavoritosUiState> { lista ->
            FavoritosUiState.Success(lista)
        }
        .catch {
            emit(FavoritosUiState.Error("No se pudieron cargar los favoritos"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FavoritosUiState.Loading
        )

    // Acción del usuario: quita (o vuelve a poner) un alojamiento de favoritos.
    fun alternarFavorito(alojamientoId: Long) {
        viewModelScope.launch {
            try {
                seguimientoRepository.alternarFavorito(alojamientoId)
                canalEventos.send(UiEvent.MostrarMensaje("Favorito actualizado"))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                canalEventos.send(UiEvent.MostrarMensaje("No se pudo completar la acción"))
            }
        }
    }
}