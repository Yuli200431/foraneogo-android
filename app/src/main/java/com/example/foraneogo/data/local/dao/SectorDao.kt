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


