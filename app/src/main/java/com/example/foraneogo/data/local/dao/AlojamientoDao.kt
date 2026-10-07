package com.example.foraneogo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.foraneogo.data.local.entities.AlojamientoEntity
import kotlinx.coroutines.flow.Flow

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
