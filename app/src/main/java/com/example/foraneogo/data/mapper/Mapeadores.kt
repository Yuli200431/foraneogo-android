package com.example.foraneogo.data.mapper


import com.example.foraneogo.data.local.entities.AlojamientoEntity
import com.example.foraneogo.data.local.entities.SectorEntity
import com.example.foraneogo.data.local.entities.SeguimientoEntity
import com.example.foraneogo.data.model.Alojamiento
import com.example.foraneogo.data.model.Sector
import com.example.foraneogo.data.model.Seguimiento

fun SectorEntity.aModelo(): Sector = Sector(
    id = id,
    nombre = nombre
)

// El nombre del sector y si es favorito no viven en AlojamientoEntity,
// por eso se reciben como parámetros.
fun AlojamientoEntity.aModelo(
    sectorNombre: String,
    esFavorito: Boolean = false
): Alojamiento = Alojamiento(
    id = id,
    titulo = titulo,
    tipo = tipo,
    precioMensual = precioMensual,
    habitaciones = habitaciones,
    sectorId = sectorId,
    sectorNombre = sectorNombre,
    direccion = direccion,
    imagen = imagen,
    wifi = wifi,
    agua = agua,
    luz = luz,
    lavanderia = lavanderia,
    amoblado = amoblado,
    descripcion = descripcion,
    telefonoPropietario = telefonoPropietario,
    whatsappPropietario = whatsappPropietario,
    calificacion = calificacion,
    esFavorito = esFavorito
)

fun SeguimientoEntity.aModelo(): Seguimiento = Seguimiento(
    id = id,
    alojamientoId = alojamientoId,
    estado = estado,
    notas = notas,
    esFavorito = esFavorito,
    fechaCitaMillis = fechaCitaMillis,
    fechaCreacionMillis = fechaCreacionMillis,
    fechaActualizacionMillis = fechaActualizacionMillis
)

// Convierte una lista de alojamientos completando sector y favorito
fun List<AlojamientoEntity>.aModelos(
    sectores: List<SectorEntity>,
    favoritos: List<SeguimientoEntity>
): List<Alojamiento> {
    val nombres = sectores.associate { it.id to it.nombre }
    val idsFavoritos = favoritos.map { it.alojamientoId }.toSet()
    return map { it.aModelo(nombres[it.sectorId].orEmpty(), it.id in idsFavoritos) }
}