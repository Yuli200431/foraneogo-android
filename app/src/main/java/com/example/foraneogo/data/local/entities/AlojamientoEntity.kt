package com.example.foraneogo.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

//Tabla Maestra 2: Alojamiento
@Entity (tableName="alojamientos",
    foreignKeys = [
        ForeignKey(
            entity = SectorEntity::class,
            parentColumns = ["id"],
            childColumns = ["sectorId"],
            onDelete = ForeignKey.RESTRICT,
        )
    ],
    indices = [Index("sectorId")],
)
data class AlojamientoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val titulo: String,
    val tipo: String,
    val precioMensual: Double,
    val habitaciones: Int,
    val sectorId: Long,
    val direccion: String,
    val imagen: String?,
    val wifi: Boolean,
    val agua: Boolean,
    val luz: Boolean,
    val lavanderia: Boolean,
    val amoblado: Boolean,
    val descripcion: String,
    val telefonoPropietario: String,
    val whatsappPropietario: String,
    @ColumnInfo(defaultValue = "0")
    val calificacion: Float = 0f,
)