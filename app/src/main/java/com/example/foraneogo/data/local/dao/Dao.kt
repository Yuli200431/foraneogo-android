package com.example.foraneogo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.foraneogo.data.local.entities.AlojamientoEntity
import com.example.foraneogo.data.local.entities.SectorEntity
import com.example.foraneogo.data.local.entities.SeguimientoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SectorDao {
    @Query("SELECT * FROM sectores ORDER BY nombre ASC")
    fun observarTodos(): Flow<List<SectorEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarTodos(sectores: List<SectorEntity>)
}

@Dao
interface AlojamientoDao{
    @Query("SELECT * FROM alojamientos ORDER BY id DESC")
    fun observarTodos():Flow<List<AlojamientoEntity>>

    @Query("""
        SELECT * FROM alojamientos
        WHERE (:sectorId IS NULL OR sectorId = :sectorId)
        AND precioMensual <= :precioMax
        ORDER BY precioMensual ASC
        """)
    fun observarFiltrados(
        sectorId: Long?,
        precioMax: Double
    ): Flow<List<AlojamientoEntity>>

    @Query("SELECT * FROM alojamientos WHERE id = :id")
    fun observarPorId(id: Long): Flow<AlojamientoEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(alojamiento: AlojamientoEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodos(
        alojamientos: List<AlojamientoEntity>
    )

    @Update
    suspend fun actualizar(alojamiento: AlojamientoEntity)

    @Delete
    suspend fun eliminar(alojamiento: AlojamientoEntity)

}

@Dao
interface SeguimientoDao {
    @Query("""
        SELECT * FROM seguimientos
        ORDER BY fechaActualizacionMillis DESC
        """)
    fun observarTodos():Flow<List<SeguimientoEntity>>

    @Query("""
        SELECT * FROM seguimientos
        WHERE alojamientoId= :alojamientoId
        LIMIT 1
        """)
    fun observarPorAlojamiento(
        alojamientoId: Long
    ): Flow<SeguimientoEntity>

    @Query("""
        SELECT * FROM seguimientos
        WHERE esFavorito = 1
        """)
    fun observarFavoritos(): Flow<List<SeguimientoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardar(
        seguimiento: SeguimientoEntity
    ): Long

    @Update
    suspend fun actualizar(
        seguimiento: SeguimientoEntity
    )

    @Delete
    suspend fun eliminar(
        seguimiento: SeguimientoDao
    )
}