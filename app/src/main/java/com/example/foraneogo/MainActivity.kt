package com.example.foraneogo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.lifecycle.lifecycleScope
import com.example.foraneogo.ui.navigation.AppNavigation
import com.example.foraneogo.ui.theme.ForaneoGOTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ESTO ES TEMPORAL — solo para confirmar que la capa de Repository de B
// funciona de punta a punta (Room -> Repository). Se borra una vez que
// confirmes que todo corre bien; no se sube a GitHub.
//
// Pégalo dentro de onCreate() de tu MainActivity, DESPUÉS de super.onCreate(...)

        val contenedor = (application as com.example.foraneogo.ForaneoApp).contenedor

        lifecycleScope.launch {
            kotlinx.coroutines.delay(1500) // le da tiempo a que cargue el seed data

            // --- Catálogo ---
            contenedor.catalogoRepository.observarSectores().collect { sectores ->
                android.util.Log.d("PRUEBA_B", "Sectores: ${sectores.size}")
                sectores.forEach { android.util.Log.d("PRUEBA_B", "  -> ${it.nombre}") }
            }
        }

        lifecycleScope.launch {
            kotlinx.coroutines.delay(1500)

            contenedor.catalogoRepository.observarFiltrados(null, 2000.0, "").collect { alojamientos ->
                android.util.Log.d("PRUEBA_B", "Alojamientos (sin filtro): ${alojamientos.size}")
                alojamientos.forEach {
                    android.util.Log.d("PRUEBA_B", "  -> ${it.titulo} - $${it.precioMensual}")
                }
            }
        }

        lifecycleScope.launch {
            kotlinx.coroutines.delay(1500)

            // --- Bitácora / Favoritos ---
            contenedor.seguimientoRepository.observarBitacora().collect { bitacora ->
                android.util.Log.d("PRUEBA_B", "Bitácora: ${bitacora.size} registros")
            }
        }

        lifecycleScope.launch {
            kotlinx.coroutines.delay(1500)

            contenedor.seguimientoRepository.observarFavoritos().collect { favoritos ->
                android.util.Log.d("PRUEBA_B", "Favoritos: ${favoritos.size}")
            }
        }

        lifecycleScope.launch {
            kotlinx.coroutines.delay(2000)

            // --- Prueba de escritura: marcar el primer alojamiento como favorito ---
            val primerAlojamiento = contenedor.catalogoRepository
                .observarFiltrados(null, 2000.0, "")
                .first()
                .firstOrNull()

            if (primerAlojamiento != null) {
                contenedor.seguimientoRepository.alternarFavorito(primerAlojamiento.id)
                android.util.Log.d("PRUEBA_B", "Marcado como favorito: ${primerAlojamiento.titulo}")

                kotlinx.coroutines.delay(500)
                val favoritosDespues = contenedor.seguimientoRepository.observarFavoritos().first()
                android.util.Log.d("PRUEBA_B", "Favoritos después de marcar: ${favoritosDespues.size}")
            }
        }

        lifecycleScope.launch {
            kotlinx.coroutines.delay(1500)

            // --- Preferencias (DataStore) ---
            contenedor.preferenciasRepository.observarPrecioMaximo().collect { precio ->
                android.util.Log.d("PRUEBA_B", "Precio máximo guardado: $precio")
            }
        }
        enableEdgeToEdge()

        setContent {
            ForaneoGOTheme {
                val navController = androidx.navigation.compose.rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ForaneoGOTheme {
        Greeting("Android")
    }
}