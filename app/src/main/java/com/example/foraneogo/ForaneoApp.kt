package com.example.foraneogo

import android.app.Application
import com.example.foraneogo.di.InicializadorDatos
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class ForaneoApp : Application() {

    // Hilt solo puede inyectar en la Application por campo (por eso es lateinit).
    @Inject
    lateinit var inicializadorDatos: InicializadorDatos

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch { inicializadorDatos.cargarDatosIniciales() }
    }
}
