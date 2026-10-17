package com.example.foraneogo.di

import android.content.Context
import androidx.room.Room
import com.example.foraneogo.data.local.AppDatabase
import com.example.foraneogo.data.local.DatosIniciales
import com.example.foraneogo.data.preferences.preferenciasDataStore
import com.example.foraneogo.data.repository.PreferenciasRepository
import com.example.foraneogo.data.repository.PreferenciasRepositoryImpl
import com.example.foraneogo.data.repository.SeguimientoRepository
import com.example.foraneogo.data.repository.SeguimientoRepositoryImpl
import kotlinx.coroutines.flow.first
import com.example.foraneogo.data.repository.AlojamientoRepository
import com.example.foraneogo.data.repository.AlojamientoRepositoryImpl
import com.example.foraneogo.data.repository.SectorRepository
import com.example.foraneogo.data.repository.SectorRepositoryImpl

// Contenedor de dependencias: aquí se crea todo UNA sola vez.
class ContenedorApp(private val contexto: Context) {

    val baseDeDatos: AppDatabase by lazy {
        Room.databaseBuilder(
            contexto.applicationContext,
            AppDatabase::class.java,
            "foraneogo.db"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    // Carga los datos de ejemplo solo si todavía no hay sectores guardados
    suspend fun cargarDatosIniciales() {
        val sectores = baseDeDatos.sectorDao().observarTodos().first()
        if (sectores.isEmpty()) {
            baseDeDatos.sectorDao().insertarTodos(DatosIniciales.sectores)
            baseDeDatos.alojamientoDao().insertarTodos(DatosIniciales.alojamientos)
        }
    }


    val sectorRepository: SectorRepository by lazy {
        SectorRepositoryImpl(baseDeDatos.sectorDao())
    }

    val alojamientoRepository: AlojamientoRepository by lazy {
        AlojamientoRepositoryImpl(
            baseDeDatos.sectorDao(),
            baseDeDatos.alojamientoDao(),
            baseDeDatos.seguimientoDao()
        )
    }

    val seguimientoRepository: SeguimientoRepository by lazy {
        SeguimientoRepositoryImpl(
            baseDeDatos.sectorDao(),
            baseDeDatos.alojamientoDao(),
            baseDeDatos.seguimientoDao()
        )
    }

    val preferenciasRepository: PreferenciasRepository by lazy {
        PreferenciasRepositoryImpl(contexto.applicationContext.preferenciasDataStore)
    }
}
