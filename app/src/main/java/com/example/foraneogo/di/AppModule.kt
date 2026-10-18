package com.example.foraneogo.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.example.foraneogo.data.local.AppDatabase
import com.example.foraneogo.data.local.dao.AlojamientoDao
import com.example.foraneogo.data.local.dao.SectorDao
import com.example.foraneogo.data.local.dao.SeguimientoDao
import com.example.foraneogo.data.preferences.preferenciasDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Le dice a Hilt cómo crear la base de datos, los DAOs y el DataStore (una sola vez).
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun proveerBaseDeDatos(@ApplicationContext contexto: Context): AppDatabase =
        Room.databaseBuilder(contexto, AppDatabase::class.java, "foraneogo.db")
            .fallbackToDestructiveMigration(true)
            .build()

    @Provides
    fun proveerSectorDao(baseDeDatos: AppDatabase): SectorDao = baseDeDatos.sectorDao()

    @Provides
    fun proveerAlojamientoDao(baseDeDatos: AppDatabase): AlojamientoDao = baseDeDatos.alojamientoDao()

    @Provides
    fun proveerSeguimientoDao(baseDeDatos: AppDatabase): SeguimientoDao = baseDeDatos.seguimientoDao()

    @Provides
    @Singleton
    fun proveerDataStore(@ApplicationContext contexto: Context): DataStore<Preferences> =
        contexto.preferenciasDataStore
}
