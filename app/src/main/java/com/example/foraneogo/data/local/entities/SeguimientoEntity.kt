package com.example.foraneogo.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

//Tabla Transaccional: Seguimiento
@Entity (tableName="seguimientos",
    foreignKeys = [
        ForeignKey(
            entity = AlojamientoEntity::class,
            parentColumns = ["id"],
            childColumns = ["alojamientoId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [ Index(
        value = ["alojamientoId"],
        unique = true
    )]
)
data class SeguimientoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val alojamientoId: Long,
    val estado: EstadoSeguimiento,
    val notas: String,
    val esFavorito: Boolean = false,
    val fechaCitaMillis: Long?,
    val fechaCreacionMillis: Long,
    val fechaActualizacionMillis: Long,
)