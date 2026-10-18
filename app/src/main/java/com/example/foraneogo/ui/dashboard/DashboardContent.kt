package com.example.foraneogo.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
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
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título principal centrado (sin "¡Hola, estudiante!")
            Text(
                text = "Foráneo GO",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                ),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )

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

            // Opciones de menú estilo tarjeta con flecha
            OpcionMenuCard(
                titulo = "Alojamiento",
                icono = "🏠",
                onClick = onNavegarACatalogo
            )
            OpcionMenuCard(
                titulo = "Mi Bitácora",
                icono = "📋",
                onClick = onNavegarABitacora
            )
            OpcionMenuCard(
                titulo = "Favoritos",
                icono = "⭐",
                onClick = onNavegarAFavoritos
            )
            OpcionMenuCard(
                titulo = "Configuración",
                icono = "⚙️",
                onClick = onNavegarAConfiguracion
            )
        }
    }
}

@Composable
fun OpcionMenuCard(
    titulo: String,
    icono: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = icono, style = MaterialTheme.typography.titleLarge)
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Text(
                text = "›",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
