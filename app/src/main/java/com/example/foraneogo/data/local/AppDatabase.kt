package com.example.foraneogo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foraneogo.data.local.dao.AlojamientoDao
import com.example.foraneogo.data.local.dao.SectorDao
import com.example.foraneogo.data.local.dao.SeguimientoDao
import com.example.foraneogo.data.local.entities.AlojamientoEntity
import com.example.foraneogo.data.local.entities.Converters
import com.example.foraneogo.data.local.entities.SectorEntity
import com.example.foraneogo.data.local.entities.SeguimientoEntity


@Database(
    entities = [
        SectorEntity::class,
        AlojamientoEntity::class,
        SeguimientoEntity::class
    ],
    version=1,
    exportSchema =false,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sectorDao() : SectorDao
    abstract fun alojamientoDao(): AlojamientoDao
    abstract fun seguimientoDao(): SeguimientoDao
}