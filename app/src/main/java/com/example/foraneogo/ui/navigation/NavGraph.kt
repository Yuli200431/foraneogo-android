package com.example.foraneogo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.foraneogo.ui.bitacora.BitacoraScreen
import com.example.foraneogo.ui.catalogo.CatalogoScreen
import com.example.foraneogo.ui.configuracion.ConfiguracionScreen
import com.example.foraneogo.ui.dashboard.DashboardScreen
import com.example.foraneogo.ui.detalle.DetalleScreen
import com.example.foraneogo.ui.detalle.DetalleViewModel
import com.example.foraneogo.ui.favoritos.FavoritosScreen
import com.example.foraneogo.ui.splash.SplashScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Ruta.Splash.route
    ) {
        composable(Ruta.Splash.route) {
            SplashScreen(
                onTiempoFinalizado = {
                    navController.navigate(Ruta.Dashboard.route) {
                        popUpTo(Ruta.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Ruta.Dashboard.route) {
            DashboardScreen(
                onNavegarACatalogo = { navController.navigate(Ruta.Catalogo.route) },
                onNavegarABitacora = { navController.navigate(Ruta.Bitacora.route) },
                onNavegarAFavoritos = { navController.navigate(Ruta.Favoritos.route) },
                onNavegarAConfiguracion = { navController.navigate(Ruta.Configuracion.route) }
            )
        }

        composable(Ruta.Catalogo.route) {
            CatalogoScreen(
                onAlojamientoClick = { id: Long ->
                    navController.navigate(Ruta.Detalle.crearRuta(id))
                },
                onVolver = { navController.popBackStack() }
            )
        }

        composable(
            route = Ruta.Detalle.route,
            arguments = listOf(
                navArgument(DetalleViewModel.ARG_ALOJAMIENTO_ID) { type = NavType.LongType }
            )
        ) {
            DetalleScreen(
                onVolver = { navController.popBackStack() }
            )
        }

        composable(Ruta.Bitacora.route) {
            BitacoraScreen(
                onVolver = { navController.popBackStack() }
            )
        }

        composable(Ruta.Favoritos.route) {
            FavoritosScreen(
                onAlojamientoClick = { id: Long ->
                    navController.navigate(Ruta.Detalle.crearRuta(id))
                },
                onVolver = { navController.popBackStack() }
            )
        }

        composable(Ruta.Configuracion.route) {
            ConfiguracionScreen(
                onVolver = { navController.popBackStack() }
            )
        }
    }
}