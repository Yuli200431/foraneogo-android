package com.example.foraneogo.data.repository

import com.example.foraneogo.data.model.Alojamiento
import kotlinx.coroutines.flow.Flow

interface AlojamientoRepository {
    fun observarAlojamientos(): Flow<List<Alojamiento>>
    fun observarFiltrados(
        sectorId: Long?,      // null = todos los sectores
        precioMax: Double,
        texto: String         // "" = sin búsqueda
    ): Flow<List<Alojamiento>>
    fun observarAlojamiento(id: Long): Flow<Alojamiento?>
    fun observarTotalAlojamientos(): Flow<Int>
}