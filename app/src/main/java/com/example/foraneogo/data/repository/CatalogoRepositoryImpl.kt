package com.example.foraneogo.data.repository

import com.example.foraneogo.data.local.dao.AlojamientoDao
import com.example.foraneogo.data.local.dao.SectorDao
import com.example.foraneogo.data.local.dao.SeguimientoDao
import com.example.foraneogo.data.mapper.aModelo
import com.example.foraneogo.data.mapper.aModelos
import com.example.foraneogo.data.model.Alojamiento
import com.example.foraneogo.data.model.Sector
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class CatalogoRepositoryImpl(
    private val sectorDao: SectorDao,
    private val alojamientoDao: AlojamientoDao,
    private val seguimientoDao: SeguimientoDao
) : CatalogoRepository {

    override fun observarSectores(): Flow<List<Sector>> =
        sectorDao.observarTodos().map { lista -> lista.map { it.aModelo() } }

    override fun observarAlojamientos(): Flow<List<Alojamiento>> =
        combine(
            alojamientoDao.observarTodos(),
            sectorDao.observarTodos(),
            seguimientoDao.observarFavoritos()
        ) { alojamientos, sectores, favoritos ->
            alojamientos.aModelos(sectores, favoritos)
        }

    override fun observarFiltrados(
        sectorId: Long?,
        precioMax: Double,
        texto: String
    ): Flow<List<Alojamiento>> =
        combine(
            alojamientoDao.observarFiltrados(sectorId, precioMax),
            sectorDao.observarTodos(),
            seguimientoDao.observarFavoritos()
        ) { alojamientos, sectores, favoritos ->
            val lista = alojamientos.aModelos(sectores, favoritos)
            val busqueda = texto.trim()
            if (busqueda.isEmpty()) {
                lista
            } else {
                lista.filter {
                    it.titulo.contains(busqueda, ignoreCase = true) ||
                            it.direccion.contains(busqueda, ignoreCase = true) ||
                            it.sectorNombre.contains(busqueda, ignoreCase = true)
                }
            }
        }

    override fun observarAlojamiento(id: Long): Flow<Alojamiento?> =
        combine(
            alojamientoDao.observarPorId(id),
            sectorDao.observarTodos(),
            seguimientoDao.observarFavoritos()
        ) { alojamiento, sectores, favoritos ->
            alojamiento?.let { listOf(it).aModelos(sectores, favoritos).first() }
        }

    override fun observarTotalAlojamientos(): Flow<Int> =
        alojamientoDao.observarTodos().map { it.size }
}