package com.example.foraneogo.data.model
import com.example.foraneogo.data.local.entities.EstadoSeguimiento

data class Sector(
    val id: Long,
    val nombre: String
)

data class Alojamiento(
    val id: Long,
    val titulo: String,
    val tipo: String,
    val precioMensual: Double,
    val habitaciones: Int,
    val sectorId: Long,
    val sectorNombre: String,
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
    val calificacion: Float,
    val esFavorito: Boolean = false
)

data class Seguimiento(
    val id: Long,
    val alojamientoId: Long,
    val estado: EstadoSeguimiento,
    val notas: String,
    val esFavorito: Boolean,
    val fechaCitaMillis: Long?,
    val fechaCreacionMillis: Long,
    val fechaActualizacionMillis: Long
)

data class SeguimientoConAlojamiento(
    val seguimiento: Seguimiento,
    val alojamiento: Alojamiento
)