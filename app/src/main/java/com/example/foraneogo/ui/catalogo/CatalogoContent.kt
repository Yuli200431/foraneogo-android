package com.example.foraneogo.ui.catalogo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.foraneogo.ui.common.ImagenAlojamiento

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoContent(
    estado: CatalogoUiState,
    onAlojamientoClick: (Long) -> Unit,
    onVolver: () -> Unit,
    onBuscarCambio: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Alojamientos",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("‹", style = MaterialTheme.typography.headlineMedium)
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (estado) {
                CatalogoUiState.Loading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

                is CatalogoUiState.Error -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = estado.mensaje, color = MaterialTheme.colorScheme.error)
                }

                is CatalogoUiState.Success -> {
                    if (estado.estaVacia) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No hay alojamientos disponibles")
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(
                                items = estado.alojamientos,
                                key = { it.id }
                            ) { alojamiento ->
                                AlojamientoCard(
                                    alojamiento = alojamiento,
                                    onClick = { onAlojamientoClick(alojamiento.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AlojamientoCard(
    alojamiento: com.example.foraneogo.data.model.Alojamiento,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen miniatura redondeada
            ImagenAlojamiento(
                nombreImagen = alojamiento.imagen,
                descripcion = alojamiento.titulo,
                modifier = Modifier
                    .size(88.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            // Información del alojamiento
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "⭐ ${alojamiento.calificacion} • ${alojamiento.sectorNombre}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = alojamiento.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$${alojamiento.precioMensual.toInt()}/mes",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Botón flotante con flecha
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "›",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
