package com.example.foraneogo.ui.detalle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.example.foraneogo.ui.common.ImagenAlojamiento

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleContent(
    estado: DetalleUiState,
    snackbarHostState: SnackbarHostState,
    onVolver: () -> Unit,
    onAlternarFavorito: () -> Unit,
    onCambiarEstado: (EstadoSeguimiento) -> Unit,
    onCambiarNotas: (String) -> Unit,
    onGuardar: () -> Unit,
    onEliminar: () -> Unit,
    onContactarWhatsApp: (String) -> Unit,
    onLlamar: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Alojamiento") },
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
            DetalleUiState.Loading -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            is DetalleUiState.Error -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(text = estado.mensaje, color = MaterialTheme.colorScheme.error)
            }

            is DetalleUiState.Success -> {
                val alojamiento = estado.alojamiento
                val precio = "%.2f".format(alojamiento.precioMensual)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ImagenAlojamiento(
                        nombreImagen = alojamiento.imagen,
                        descripcion = alojamiento.titulo,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                    )

                    Text(
                        text = alojamiento.titulo,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = "$$precio / mes",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "${alojamiento.sectorNombre} · ${alojamiento.direccion}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Calificación: ${alojamiento.calificacion}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = alojamiento.descripcion,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    ServiciosAlojamiento(alojamiento = alojamiento)

                    OutlinedButton(
                        onClick = onAlternarFavorito,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            if (estado.esFavorito) "Quitar de favoritos" else "Marcar como favorito"
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { onContactarWhatsApp(alojamiento.whatsappPropietario) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("WhatsApp")
                        }
                        OutlinedButton(
                            onClick = { onLlamar(alojamiento.telefonoPropietario) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Llamar")
                        }
                    }

                    FormularioSeguimiento(
                        estadoElegido = estado.estadoElegido,
                        notas = estado.notas,
                        fechaCitaMillis = estado.fechaCitaMillis,
                        tieneSeguimiento = estado.tieneSeguimiento,
                        onCambiarEstado = onCambiarEstado,
                        onCambiarNotas = onCambiarNotas,
                        onGuardar = onGuardar,
                        onEliminar = onEliminar
                    )
                }
            }
        }
    }
}