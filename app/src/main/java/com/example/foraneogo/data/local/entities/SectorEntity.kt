package com.example.foraneogo.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName="sectores")
data class SectorEntity(
    @PrimaryKey(autogenerate = true)
    val id: Int = 0,
    val nombre: String
)