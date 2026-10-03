package com.example.foraneogo.data.repository

import com.example.foraneogo.data.local.dao.AlojamientoDao
import com.example.foraneogo.data.local.dao.SectorDao
import com.example.foraneogo.data.local.dao.SeguimientoDao
import com.example.foraneogo.data.local.entities.EstadoSeguimiento
import com.example.foraneogo.data.local.entities.SeguimientoEntity
import com.example.foraneogo.data.mapper.aModelo
import com.example.foraneogo.data.mapper.aModelos
import com.example.foraneogo.data.model.Alojamiento
import com.example.foraneogo.data.model.Seguimiento
import com.example.foraneogo.data.model.SeguimientoConAlojamiento
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class SeguimientoRepositoryImpl(
    private val sectorDao: SectorDao,
    private val alojamientoDao: AlojamientoDao,
    private val seguimientoDao: SeguimientoDao
) : SeguimientoRepository {

    override fun observarBitacora(): Flow<List<SeguimientoConAlojamiento>> =
        combine(
            seguimientoDao.observarTodos(),
            alojamientoDao.observarTodos(),
            sectorDao.observarTodos()
        ) { seguimientos, alojamientos, sectores ->
            val favoritos = seguimientos.filter { it.esFavorito }
            val porId = alojamientos.aModelos(sectores, favoritos).associateBy { it.id }
            seguimientos.mapNotNull { s ->
                porId[s.alojamientoId]?.let { SeguimientoConAlojamiento(s.aModelo(), it) }
            }
        }

    override fun observarFavoritos(): Flow<List<Alojamiento>> =
        combine(
            seguimientoDao.observarFavoritos(),
            alojamientoDao.observarTodos(),
            sectorDao.observarTodos()
        ) { favoritos, alojamientos, sectores ->
            alojamientos.aModelos(sectores, favoritos).filter { it.esFavorito }
        }

    // No usamos observarPorAlojamiento del DAO (devuelve no nulo y podría fallar)
    override fun observarSeguimiento(alojamientoId: Long): Flow<Seguimiento?> =
        seguimientoDao.observarTodos().map { lista ->
            lista.firstOrNull { it.alojamientoId == alojamientoId }?.aModelo()
        }

    override fun observarTotalBitacora(): Flow<Int> =
        seguimientoDao.observarTodos().map { it.size }

    override fun observarTotalFavoritos(): Flow<Int> =
        seguimientoDao.observarFavoritos().map { it.size }

    override suspend fun guardarSeguimiento(
        alojamientoId: Long,
        estado: EstadoSeguimiento,
        notas: String,
        fechaCitaMillis: Long?
    ) {
        val ahora = System.currentTimeMillis()
        val existente = buscar(alojamientoId)
        if (existente == null) {
            seguimientoDao.guardar(
                SeguimientoEntity(
                    alojamientoId = alojamientoId,
                    estado = estado,
                    notas = notas,
                    esFavorito = false,
                    fechaCitaMillis = fechaCitaMillis,
                    fechaCreacionMillis = ahora,
                    fechaActualizacionMillis = ahora
                )
            )
        } else {
            seguimientoDao.actualizar(
                existente.copy(
                    estado = estado,
                    notas = notas,
                    fechaCitaMillis = fechaCitaMillis,
                    fechaActualizacionMillis = ahora
                )
            )
        }
    }

    override suspend fun alternarFavorito(alojamientoId: Long) {
        val existente = buscar(alojamientoId)
        if (existente == null) {
            val ahora = System.currentTimeMillis()
            seguimientoDao.guardar(
                SeguimientoEntity(
                    alojamientoId = alojamientoId,
                    estado = EstadoSeguimiento.PENDIENTE,
                    notas = "",
                    esFavorito = true,
                    fechaCitaMillis = null,
                    fechaCreacionMillis = ahora,
                    fechaActualizacionMillis = ahora
                )
            )
        } else {
            seguimientoDao.actualizar(existente.copy(esFavorito = !existente.esFavorito))
        }
    }

    override suspend fun eliminarSeguimiento(alojamientoId: Long) {
        buscar(alojamientoId)?.let { seguimientoDao.eliminar(it) }
    }

    private suspend fun buscar(alojamientoId: Long): SeguimientoEntity? =
        seguimientoDao.observarTodos().first()
            .firstOrNull { it.alojamientoId == alojamientoId }
}