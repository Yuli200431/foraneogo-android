package com.example.foraneogo.data.repository

import com.example.foraneogo.data.model.Sector
import kotlinx.coroutines.flow.Flow

interface SectorRepository {
    fun observarSectores(): Flow<List<Sector>>
}