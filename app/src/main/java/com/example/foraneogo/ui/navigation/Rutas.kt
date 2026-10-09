package com.example.foraneogo.ui.navigation

import com.example.foraneogo.ui.detalle.DetalleViewModel

sealed class Ruta(val route: String) {
    object Splash : Ruta("splash")
    object Dashboard : Ruta("dashboard")
    object Catalogo : Ruta("catalogo")
    object Detalle : Ruta("detalle/{${DetalleViewModel.ARG_ALOJAMIENTO_ID}}") {
        fun crearRuta(alojamientoId: Long) = "detalle/$alojamientoId"
    }
    object Bitacora : Ruta("bitacora")
    object Favoritos : Ruta("favoritos")
    object Configuracion : Ruta("configuracion")
}