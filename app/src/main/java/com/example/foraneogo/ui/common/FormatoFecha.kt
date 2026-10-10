package com.example.foraneogo.ui.common


import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private val formatoFecha: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

fun formatearFecha(millis: Long): String =
    Instant.ofEpochMilli(millis)
        .atZone(ZoneId.systemDefault())
        .format(formatoFecha)