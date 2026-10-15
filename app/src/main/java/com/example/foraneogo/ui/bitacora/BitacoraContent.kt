package com.example.foraneogo.ui.bitacora

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.foraneogo.data.local.entities.EstadoSeguimiento
import com.example.foraneogo.data.model.SeguimientoConAlojamiento

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BitacoraContent(
    estado: BitacoraUiState,
    snackbarHostState: SnackbarHostState,
    onVolver: () -> Unit,
    onCambiarEstado: (SeguimientoConAlojamiento, EstadoSeguimiento) -> Unit,
    onEliminar: (Long) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bitácora de Seguimiento") },
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
            BitacoraUiState.Loading -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            is BitacoraUiState.Error -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(text = estado.mensaje, color = MaterialTheme.colorScheme.error)
            }

            is BitacoraUiState.Success -> {
                if (estado.estaVacia) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No hay registros en la bitácora")
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = estado.seguimientos,
                            key = { it.seguimiento.id }
                        ) { item ->
                            TarjetaSeguimiento(
                                item = item,
                                onCambiarEstado = { nuevo -> onCambiarEstado(item, nuevo) },
                                onEliminar = { onEliminar(item.alojamiento.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}