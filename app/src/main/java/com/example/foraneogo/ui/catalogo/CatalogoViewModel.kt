package com.example.foraneogo.ui.catalogo



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foraneogo.data.repository.CatalogoRepository
import com.example.foraneogo.data.repository.PreferenciasRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CatalogoViewModel(
    catalogoRepository: CatalogoRepository,
    preferenciasRepository: PreferenciasRepository
) : ViewModel() {

    // Filtros actuales. Es privado: la pantalla solo los ve a través del estado.
    private data class Filtros(
        val sectorId: Long? = null,
        val precioMax: Double = 2000.0,
        val texto: String = ""
    )

    private val filtros = MutableStateFlow(Filtros())

    // Al abrir, los filtros arrancan con las preferencias guardadas del usuario.
    init {
        viewModelScope.launch {
            val sectorPreferido = preferenciasRepository.observarSectorPreferido().first()
            val precioPreferido = preferenciasRepository.observarPrecioMaximo().first()
            filtros.update { it.copy(sectorId = sectorPreferido, precioMax = precioPreferido) }
        }
    }

    // Único estado que expone este ViewModel.
    @OptIn(ExperimentalCoroutinesApi::class)
    val estado: StateFlow<CatalogoUiState> = filtros
        .flatMapLatest { f ->
            combine(
                catalogoRepository.observarFiltrados(f.sectorId, f.precioMax, f.texto),
                catalogoRepository.observarSectores()
            ) { alojamientos, sectores ->
                CatalogoUiState(
                    cargando = false,
                    alojamientos = alojamientos,
                    sectores = sectores,
                    sectorSeleccionadoId = f.sectorId,
                    precioMaximo = f.precioMax,
                    textoBusqueda = f.texto
                )
            }
        }
        .catch {
            emit(CatalogoUiState(cargando = false, error = "No se pudo cargar el catálogo"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CatalogoUiState()
        )

    // Acciones del usuario. sectorId = null significa "todos los sectores".
    fun cambiarSector(sectorId: Long?) {
        filtros.update { it.copy(sectorId = sectorId) }
    }

    fun cambiarPrecioMaximo(precio: Double) {
        filtros.update { it.copy(precioMax = precio) }
    }

    fun cambiarTexto(texto: String) {
        filtros.update { it.copy(texto = texto) }
    }
}