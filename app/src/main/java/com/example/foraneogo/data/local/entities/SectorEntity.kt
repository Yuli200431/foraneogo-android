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

//Estado de bitácora
enum class EstadoSeguimiento{
    PENDIENTE,
    CONTACTADO,
    PENDIENTE_VISITA,
    DESCARTADO,
}

class Converters{
    @TypeConverter
    fun fromEstado(valor: EstadoSeguimiento): String {
        return valor.name
    }

    @TypeConverter
    fun toEstado (valor: String) : EstadoSeguimiento {
        return EstadoSeguimiento.valueOf(valor)
    }
}
