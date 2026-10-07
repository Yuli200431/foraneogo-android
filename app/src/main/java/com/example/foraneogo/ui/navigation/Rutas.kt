package com.example.foraneogo.ui.navigation

sealed class Ruta(val route: String) {
    object Splash : Ruta("splash")
    object Dashboard : Ruta("dashboard")
    object Catalogo : Ruta("catalogo")
    object Detalle : Ruta("detalle/{alojamientoId}") {
        fun crearRuta(alojamientoId: Long) = "detalle/$alojamientoId"
    }
    object Bitacora : Ruta("bitacora")
    object Favoritos : Ruta("favoritos")
    object Configuracion : Ruta("configuracion")
}