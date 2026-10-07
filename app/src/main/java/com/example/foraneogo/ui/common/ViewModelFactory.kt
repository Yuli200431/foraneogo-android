package com.example.foraneogo.ui.common


import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.foraneogo.ForaneoApp
import com.example.foraneogo.ui.bitacora.BitacoraViewModel
import com.example.foraneogo.ui.catalogo.CatalogoViewModel
import com.example.foraneogo.ui.configuracion.ConfiguracionViewModel
import com.example.foraneogo.ui.dashboard.DashboardViewModel
import com.example.foraneogo.ui.detalle.DetalleViewModel
import com.example.foraneogo.ui.favoritos.FavoritosViewModel

// Fábrica única: arma cada ViewModel con los repositorios del ContenedorApp.
object ViewModelFactory {

    val Factory = viewModelFactory {

        initializer {
            val contenedor = (this[APPLICATION_KEY] as ForaneoApp).contenedor
            DashboardViewModel(
                catalogoRepository = contenedor.catalogoRepository,
                seguimientoRepository = contenedor.seguimientoRepository
            )
        }

        initializer {
            val contenedor = (this[APPLICATION_KEY] as ForaneoApp).contenedor
            CatalogoViewModel(
                catalogoRepository = contenedor.catalogoRepository,
                preferenciasRepository = contenedor.preferenciasRepository
            )
        }

        initializer {
            val contenedor = (this[APPLICATION_KEY] as ForaneoApp).contenedor
            DetalleViewModel(
                savedStateHandle = createSavedStateHandle(),
                catalogoRepository = contenedor.catalogoRepository,
                seguimientoRepository = contenedor.seguimientoRepository
            )
        }

        initializer {
            val contenedor = (this[APPLICATION_KEY] as ForaneoApp).contenedor
            BitacoraViewModel(
                seguimientoRepository = contenedor.seguimientoRepository
            )
        }

        initializer {
            val contenedor = (this[APPLICATION_KEY] as ForaneoApp).contenedor
            FavoritosViewModel(
                seguimientoRepository = contenedor.seguimientoRepository
            )
        }

        initializer {
            val contenedor = (this[APPLICATION_KEY] as ForaneoApp).contenedor
            ConfiguracionViewModel(
                catalogoRepository = contenedor.catalogoRepository,
                preferenciasRepository = contenedor.preferenciasRepository
            )
        }
    }
}