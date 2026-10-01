package com.example.foraneogo.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Esto normalmente lo arma Persona B al construir
// el contenedor de dependencias. Pero como lo necesitaba
// probar mi parte, se creo de forma temporarl: cuando B arme el
// contenedor final, puede reutilizar esta misma
// función tal cual (Pablo decide).

fun construirAppDatabase(context: Context): AppDatabase {
    lateinit var instancia: AppDatabase

    instancia = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "foraneogo.db"
    )
        .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // onCreate SOLO se dispara la primera vez que el archivo
                // de la base de datos se crea en el dispositivo. Por eso
                // es seguro: nunca se van a duplicar los datos en los
                // siguientes arranques de la app.
                CoroutineScope(Dispatchers.IO).launch {
                    instancia.sectorDao().insertarTodos(DatosIniciales.sectores)
                    instancia.alojamientoDao().insertarTodos(DatosIniciales.alojamientos)
                }
            }
        })
        .build()

    return instancia
}