package com.example.foraneogo

import android.app.Application
import com.example.foraneogo.di.ContenedorApp

class ForaneoApp : Application() {
    val contenedor: ContenedorApp by lazy { ContenedorApp(this) }
}