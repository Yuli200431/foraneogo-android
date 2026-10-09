package com.example.foraneogo

import android.app.Application
import com.example.foraneogo.di.ContenedorApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ForaneoApp : Application() {
    val contenedor: ContenedorApp by lazy { ContenedorApp(this) }

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch { contenedor.cargarDatosIniciales() }
    }
}