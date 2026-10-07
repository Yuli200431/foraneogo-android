package com.example.foraneogo.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardContent(
    estado: DashboardUiState,
    onNavegarACatalogo: () -> Unit,
    onNavegarABitacora: () -> Unit,
    onNavegarAFavoritos: () -> Unit,
    onNavegarAConfiguracion: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard - ForaneoGO") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Total Alojamientos: ${estado.totalAlojamientos}")
            Text("Total Bitácora: ${estado.totalBitacora}")
            Text("Total Favoritos: ${estado.totalFavoritos}")

            Button(onClick = onNavegarACatalogo, modifier = Modifier.padding(top = 8.dp)) {
                Text("Ir al Catálogo")
            }
            Button(onClick = onNavegarABitacora, modifier = Modifier.padding(top = 8.dp)) {
                Text("Ir a Bitácora")
            }
            Button(onClick = onNavegarAFavoritos, modifier = Modifier.padding(top = 8.dp)) {
                Text("Ir a Favoritos")
            }
            Button(onClick = onNavegarAConfiguracion, modifier = Modifier.padding(top = 8.dp)) {
                Text("Configuración")
            }
        }
    }
}
