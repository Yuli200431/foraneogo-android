package com.example.foraneogo.data.repository

import com.example.foraneogo.data.local.dao.SectorDao
import com.example.foraneogo.data.mapper.aModelo
import com.example.foraneogo.data.model.Sector
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SectorRepositoryImpl @Inject constructor(
    private val sectorDao: SectorDao
) : SectorRepository {

    override fun observarSectores(): Flow<List<Sector>> =
        sectorDao.observarTodos().map { lista -> lista.map { it.aModelo() } }
}