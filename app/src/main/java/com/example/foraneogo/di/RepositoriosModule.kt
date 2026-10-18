package com.example.foraneogo.di

import com.example.foraneogo.data.repository.AlojamientoRepository
import com.example.foraneogo.data.repository.AlojamientoRepositoryImpl
import com.example.foraneogo.data.repository.PreferenciasRepository
import com.example.foraneogo.data.repository.PreferenciasRepositoryImpl
import com.example.foraneogo.data.repository.SectorRepository
import com.example.foraneogo.data.repository.SectorRepositoryImpl
import com.example.foraneogo.data.repository.SeguimientoRepository
import com.example.foraneogo.data.repository.SeguimientoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Une cada interfaz de repositorio con su implementación.
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriosModule {

    @Binds
    @Singleton
    abstract fun enlazarSectorRepository(impl: SectorRepositoryImpl): SectorRepository

    @Binds
    @Singleton
    abstract fun enlazarAlojamientoRepository(impl: AlojamientoRepositoryImpl): AlojamientoRepository

    @Binds
    @Singleton
    abstract fun enlazarSeguimientoRepository(impl: SeguimientoRepositoryImpl): SeguimientoRepository

    @Binds
    @Singleton
    abstract fun enlazarPreferenciasRepository(impl: PreferenciasRepositoryImpl): PreferenciasRepository
}
