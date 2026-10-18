package com.example.foraneogo.ui.configuracion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfiguracionContent(
    estado: ConfiguracionUiState,
    snackbarHostState: SnackbarHostState,
    onVolver: () -> Unit,
    onCambiarPrecio: (String) -> Unit,
    onGuardarPrecio: () -> Unit,
    onGuardarSector: (Long?) -> Unit,
    onGuardarModoOscuro: (Boolean) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configuración") },
                navigationIcon = {
                    Button(onClick = onVolver) {
                        Text("Volver")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        when (estado) {
            ConfiguracionUiState.Loading -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            is ConfiguracionUiState.Error -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(text = estado.mensaje, color = MaterialTheme.colorScheme.error)
            }

            is ConfiguracionUiState.Success -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "Sector Habitual", style = MaterialTheme.typography.titleMedium)

                // Opción "Todos"
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = estado.sectorPreferidoId == null,
                        onClick = { onGuardarSector(null) }
                    )
                    Text(text = "Todos los sectores", modifier = Modifier.padding(start = 8.dp))
                }

                // Lista de sectores
                estado.sectores.forEach { sector ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = estado.sectorPreferidoId == sector.id,
                            onClick = { onGuardarSector(sector.id) }
                        )
                        Text(text = sector.nombre, modifier = Modifier.padding(start = 8.dp))
                    }
                }

                Text(text = "Presupuesto máximo ($)", style = MaterialTheme.typography.titleMedium)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = estado.precioTexto,
                        onValueChange = onCambiarPrecio,
                        label = { Text("Precio máx") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    Button(onClick = onGuardarPrecio) {
                        Text("Guardar")
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Modo Oscuro")
                    Switch(
                        checked = estado.modoOscuro,
                        onCheckedChange = onGuardarModoOscuro
                    )
                }
            }
        }
    }
}
