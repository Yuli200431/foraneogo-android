package com.example.foraneogo.data.local.entities

import androidx.room.TypeConverter

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
