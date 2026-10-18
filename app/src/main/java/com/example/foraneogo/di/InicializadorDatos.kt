package com.example.foraneogo.di

import com.example.foraneogo.data.local.DatosIniciales
import com.example.foraneogo.data.local.dao.AlojamientoDao
import com.example.foraneogo.data.local.dao.SectorDao
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

// Carga los datos de ejemplo solo si todavía no hay sectores guardados.
@Singleton
class InicializadorDatos @Inject constructor(
    private val sectorDao: SectorDao,
    private val alojamientoDao: AlojamientoDao
) {
    suspend fun cargarDatosIniciales() {
        val sectores = sectorDao.observarTodos().first()
        if (sectores.isEmpty()) {
            sectorDao.insertarTodos(DatosIniciales.sectores)
            alojamientoDao.insertarTodos(DatosIniciales.alojamientos)
        }
    }
}
