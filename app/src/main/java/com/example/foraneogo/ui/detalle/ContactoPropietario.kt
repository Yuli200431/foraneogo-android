package com.example.foraneogo.ui.detalle

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

// Ecuador: se quita el 0 inicial y se antepone el código del país (593).
private fun aNumeroInternacional(numero: String): String =
    "593" + numero.filter { it.isDigit() }.trimStart('0')

fun abrirWhatsApp(contexto: Context, numero: String) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("https://wa.me/${aNumeroInternacional(numero)}")
    )
    abrir(contexto, intent, "No se pudo abrir WhatsApp")
}

fun abrirMarcador(contexto: Context, numero: String) {
    val intent = Intent(
        Intent.ACTION_DIAL,
        Uri.parse("tel:${numero.filter { it.isDigit() }}")
    )
    abrir(contexto, intent, "No se pudo abrir el teléfono")
}

private fun abrir(contexto: Context, intent: Intent, mensajeError: String) {
    try {
        contexto.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(contexto, mensajeError, Toast.LENGTH_SHORT).show()
    }
}