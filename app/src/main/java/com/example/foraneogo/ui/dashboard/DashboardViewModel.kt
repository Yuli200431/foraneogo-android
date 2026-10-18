package com.example.foraneogo.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foraneogo.data.repository.AlojamientoRepository
import com.example.foraneogo.data.repository.SeguimientoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    alojamientoRepository: AlojamientoRepository,
    seguimientoRepository: SeguimientoRepository
) : ViewModel() {

    // Único estado que expone este ViewModel. Se arma juntando los 3 totales.
    val estado: StateFlow<DashboardUiState> = combine<Int, Int, Int, DashboardUiState>(
        alojamientoRepository.observarTotalAlojamientos(),
        seguimientoRepository.observarTotalBitacora(),
        seguimientoRepository.observarTotalFavoritos()
    ) { alojamientos, bitacora, favoritos ->
        DashboardUiState.Success(
            totalAlojamientos = alojamientos,
            totalBitacora = bitacora,
            totalFavoritos = favoritos
        )
    }
        .catch {
            emit(DashboardUiState.Error("No se pudieron cargar los datos"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DashboardUiState.Loading
        )
}