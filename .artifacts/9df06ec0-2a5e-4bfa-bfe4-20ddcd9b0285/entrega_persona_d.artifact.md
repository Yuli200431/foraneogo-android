📦 ENTREGA PERSONA D (Pantallas, Navegación y Arquitectura UI), para el equipo y @Bryan

Estado: Rama `feature/ui-compose-navigation`. Integrado y conectado con los ViewModels y estados de la Persona C.

1. QUÉ HICE
- **Sistema de Rutas (`Rutas.kt`)**: Definición centralizada de todas las rutas de la app (`Splash`, `Dashboard`, `Catalogo`, `Detalle`, `Bitacora`, `Favoritos`, `Configuracion`), incluyendo el manejo del argumento `alojamientoId` para la pantalla de detalle.
- **Grafo de Navegación (`NavGraph.kt`)**: Implementación del `NavHost` completo que conecta las 7 pantallas, estableciendo el Splash como pantalla inicial y extrayendo correctamente el ID del alojamiento en la ruta de detalle (`NavType.LongType`).
- **Arquitectura de Contenedor y Vista Pura (Separación en 2 archivos por pantalla)**:
  - Cada una de las 7 pantallas cuenta con su **Contenedor (`*Screen.kt`)** —que es el único que conoce al `ViewModel` mediante `ViewModelFactory.Factory` y observa el estado— y su **Vista Pura / Content (`*Content.kt`)** —un componente puramente visual (stateless) que solo recibe datos listos y funciones de eventos (`on...`), cumpliendo la regla de oro del profesor.
- **Punto de Entrada (`MainActivity.kt`)**: Actualizado para inicializar `AppNavigation` con el `NavHostController` de Compose.

Archivos creados/modificados en `ui/`:
- `ui/navigation/Rutas.kt`
- `ui/navigation/NavGraph.kt`
- `ui/splash/SplashScreen.kt` (Contenedor) & `SplashContent.kt` (Vista pura)
- `ui/dashboard/DashboardScreen.kt` (Contenedor) & `DashboardContent.kt` (Vista pura)
- `ui/catalogo/CatalogoScreen.kt` (Contenedor) & `CatalogoContent.kt` (Vista pura)
- `ui/detalle/DetalleScreen.kt` (Contenedor) & `DetalleContent.kt` (Vista pura)
- `ui/bitacora/BitacoraScreen.kt` (Contenedor) & `BitacoraContent.kt` (Vista pura)
- `ui/favoritos/FavoritosScreen.kt` (Contenedor) & `FavoritosContent.kt` (Vista pura)
- `ui/configuracion/ConfiguracionScreen.kt` (Contenedor) & `ConfiguracionContent.kt` (Vista pura)

2. CÓMO SE INTEGRA
- Los contenedores llaman a los ViewModels de la Persona C usando `ViewModelFactory.Factory`:
  ```kotlin
  val viewModel: CatalogoViewModel = viewModel(factory = ViewModelFactory.Factory)
  val estado by viewModel.estado.collectAsState()
  ```
- La navegación hacia el detalle pasa el ID correctamente:
  ```kotlin
  navController.navigate(Ruta.Detalle.crearRuta(id))
  ```
- El detalle recibe el `alojamientoId` desde los argumentos de la ruta y lo pasa a su ViewModel/Estado.

3. QUÉ PROBÉ
- **Compilación**: El proyecto compila al 100% sin errores (`Build finished successfully`).
- **Navegación y Estados**: Las 7 pantallas están conectadas a sus respectivos `UiState` y ViewModels de la Persona C. Las vistas puras renderizan el estado de carga, listas vacías, elementos y botones de retroceso o navegación de forma correcta.

4. QUÉ FALTA O PRÓXIMOS PASOS (Mejoras opcionales)
- Conectar los eventos de Snackbar (`vm.eventos`) en las pantallas que los emiten (Detalle, Bitácora, Favoritos, Configuración).
- Agregar interacciones avanzadas en las vistas puras (filtros por texto, selectores de estado de seguimiento, etc.) aprovechando que el estado y las acciones ya están expuestas por los ViewModels de la Persona C.
