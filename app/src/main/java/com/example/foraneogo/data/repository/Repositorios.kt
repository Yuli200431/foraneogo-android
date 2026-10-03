package com.example.foraneogo.data.repository

import com.example.foraneogo.data.local.entities.EstadoSeguimiento
import com.example.foraneogo.data.model.Alojamiento
import com.example.foraneogo.data.model.Sector
import com.example.foraneogo.data.model.Seguimiento
import com.example.foraneogo.data.model.SeguimientoConAlojamiento
import kotlinx.coroutines.flow.Flow


//Catalogo: alojamiento + sectores
interface CatalogoRepository{
    fun observarSectores(): Flow<List<Sector>>
    fun observarAlojamientos(): Flow<List<Alojamiento>>
    fun observarFiltrados(
        sectorId: Long?,
        precioMax: Double,
        texto: String
    ): Flow<List<Alojamiento>>
    fun observarAlojamiento(id: Long): Flow<Alojamiento?>
    fun observarTotalAlojamientos(): Flow<Int>
}

//Bitacora y favoritos
interface SeguimientoRepository{
    fun observarBitacora(): Flow<List<SeguimientoConAlojamiento>>
    fun observarFavoritos(): Flow<List<Alojamiento>>
    fun observarSeguimiento(alojamientoId: Long): Flow<Seguimiento?>
    fun observarTotalBitacora(): Flow<Int>
    fun observarTotalFavoritos(): Flow<Int>

    suspend fun guardarSeguimiento(
        alojamientoId: Long,
        estado: EstadoSeguimiento,
        notas: String,
        fechaCitasMillis: Long?
    )
    suspend fun alternarFavorito(alojamientoId: Long)
    suspend fun eliminarSeguimiento(alojamientoId: Long)
}

//Preferencias de usuario
interface PreferenciasRepository{
    fun observarSectorPreferido(): Flow<Long?>
    fun observarPrecioMaximo(): Flow<Double>
    fun observarModoOscuro(): Flow<Boolean>

    suspend fun guardarSectorPreferido(sectorId: Long?)
    suspend fun guardarPrecioMaximo(precio: Double)
    suspend fun guardarModoOscuro(activado: Boolean)
}