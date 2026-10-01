package com.example.foraneogo.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey



//Estado de bitácora
enum class EstadoSeguimiento{
    PENDIENTE,
    CONTACTADO,
    PENDIENTE_VISITA,
    DESCARTADO,
}
