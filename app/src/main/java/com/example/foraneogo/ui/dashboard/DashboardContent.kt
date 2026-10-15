package com.example.foraneogo.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
                title = { Text("ForáneoGO") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (estado) {
                DashboardUiState.Loading -> Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(96.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

                is DashboardUiState.Success -> Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TarjetaResumen(
                        valor = estado.totalAlojamientos,
                        etiqueta = "Alojamientos",
                        modifier = Modifier.weight(1f)
                    )
                    TarjetaResumen(
                        valor = estado.totalBitacora,
                        etiqueta = "En bitácora",
                        modifier = Modifier.weight(1f)
                    )
                    TarjetaResumen(
                        valor = estado.totalFavoritos,
                        etiqueta = "Favoritos",
                        modifier = Modifier.weight(1f)
                    )
                }

                is DashboardUiState.Error -> Text(
                    text = estado.mensaje,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Button(onClick = onNavegarACatalogo, modifier = Modifier.fillMaxWidth()) {
                Text("Ver catálogo")
            }
            Button(onClick = onNavegarABitacora, modifier = Modifier.fillMaxWidth()) {
                Text("Mi bitácora")
            }
            Button(onClick = onNavegarAFavoritos, modifier = Modifier.fillMaxWidth()) {
                Text("Mis favoritos")
            }
            OutlinedButton(onClick = onNavegarAConfiguracion, modifier = Modifier.fillMaxWidth()) {
                Text("Configuración")
            }
        }
    }
}