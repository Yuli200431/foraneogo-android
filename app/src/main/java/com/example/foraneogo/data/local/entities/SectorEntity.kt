package com.example.foraneogo.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter


//Tabla Maestra 1: Sector
@Entity (tableName="sectores")
data class SectorEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0, //Porque Int y no Long
    val nombre: String
)




