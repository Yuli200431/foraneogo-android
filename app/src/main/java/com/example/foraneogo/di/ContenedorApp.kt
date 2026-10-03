package com.example.foraneogo.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.foraneogo.data.local.AppDatabase
import com.example.foraneogo.data.local.DatosIniciales
import com.example.foraneogo.data.preferences.preferenciasDataStore
import com.example.foraneogo.data.repository.CatalogoRepository
import com.example.foraneogo.data.repository.CatalogoRepositoryImpl
import com.example.foraneogo.data.repository.PreferenciasRepository
import com.example.foraneogo.data.repository.PreferenciasRepositoryImpl
import com.example.foraneogo.data.repository.SeguimientoRepository
import com.example.foraneogo.data.repository.SeguimientoRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Contenedor de dependencias: aquí se crea todo UNA sola vez.
class ContenedorApp(private val contexto: Context) {

    val baseDeDatos: AppDatabase by lazy {
        Room.databaseBuilder(
            contexto.applicationContext,
            AppDatabase::class.java,
            "foraneogo.db"
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // Solo se ejecuta la primera vez que se crea el archivo de la BD
                    CoroutineScope(Dispatchers.IO).launch {
                        baseDeDatos.sectorDao().insertarTodos(DatosIniciales.sectores)
                        baseDeDatos.alojamientoDao().insertarTodos(DatosIniciales.alojamientos)
                    }
                }
            })
            .build()
    }

    val catalogoRepository: CatalogoRepository by lazy {
        CatalogoRepositoryImpl(
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