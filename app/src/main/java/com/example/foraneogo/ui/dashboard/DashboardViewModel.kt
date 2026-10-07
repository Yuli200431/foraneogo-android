package com.example.foraneogo.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foraneogo.data.repository.CatalogoRepository
import com.example.foraneogo.data.repository.SeguimientoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class DashboardViewModel(
    catalogoRepository: CatalogoRepository,
    seguimientoRepository: SeguimientoRepository
) : ViewModel() {

    // Único estado que expone este ViewModel. Se arma juntando los 3 totales.
    val estado: StateFlow<DashboardUiState> = combine(
        catalogoRepository.observarTotalAlojamientos(),
        seguimientoRepository.observarTotalBitacora(),
        seguimientoRepository.observarTotalFavoritos()
    ) { alojamientos, bitacora, favoritos ->
        DashboardUiState(
            cargando = false,
            totalAlojamientos = alojamientos,
            totalBitacora = bitacora,
            totalFavoritos = favoritos
        )
    }
        .catch {
            emit(DashboardUiState(cargando = false, error = "No se pudieron cargar los datos"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DashboardUiState()
        )
}