package com.example.foraneogo.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foraneogo.data.repository.PreferenciasRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

// Lee la preferencia de modo oscuro para aplicarla al tema de toda la app.
class TemaViewModel(
    preferenciasRepository: PreferenciasRepository
) : ViewModel() {

    val modoOscuro: StateFlow<Boolean?> = preferenciasRepository.observarModoOscuro()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)
}
