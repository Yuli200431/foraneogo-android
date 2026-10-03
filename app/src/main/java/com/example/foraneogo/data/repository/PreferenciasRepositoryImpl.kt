package com.example.foraneogo.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class PreferenciasRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : PreferenciasRepository {

    private object Claves {
        val SECTOR_PREFERIDO = longPreferencesKey("sector_preferido")
        val PRECIO_MAXIMO = doublePreferencesKey("precio_maximo")
        val MODO_OSCURO = booleanPreferencesKey("modo_oscuro")
    }

    private companion object {
        const val PRECIO_MAXIMO_POR_DEFECTO = 2000.0
    }

    // Si el archivo falla al leerse, se usan los valores por defecto
    private val preferencias: Flow<Preferences> = dataStore.data.catch { error ->
        if (error is IOException) emit(emptyPreferences()) else throw error
    }

    override fun observarSectorPreferido(): Flow<Long?> =
        preferencias.map { it[Claves.SECTOR_PREFERIDO] }

    override fun observarPrecioMaximo(): Flow<Double> =
        preferencias.map { it[Claves.PRECIO_MAXIMO] ?: PRECIO_MAXIMO_POR_DEFECTO }

    override fun observarModoOscuro(): Flow<Boolean> =
        preferencias.map { it[Claves.MODO_OSCURO] ?: false }

    override suspend fun guardarSectorPreferido(sectorId: Long?) {
        dataStore.edit { datos ->
            if (sectorId == null) datos.remove(Claves.SECTOR_PREFERIDO)
            else datos[Claves.SECTOR_PREFERIDO] = sectorId
        }
    }

    override suspend fun guardarPrecioMaximo(precio: Double) {
        dataStore.edit { it[Claves.PRECIO_MAXIMO] = precio }
    }

    override suspend fun guardarModoOscuro(activado: Boolean) {
        dataStore.edit { it[Claves.MODO_OSCURO] = activado }
    }
}