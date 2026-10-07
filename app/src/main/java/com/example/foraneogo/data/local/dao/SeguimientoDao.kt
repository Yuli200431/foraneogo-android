package com.example.foraneogo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.foraneogo.data.local.entities.SeguimientoEntity
import kotlinx.coroutines.flow.Flow

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
        seguimiento: SeguimientoEntity
    )
}