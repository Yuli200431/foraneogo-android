package com.example.foraneogo.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

// Una sola instancia de DataStore para toda la app
val Context.preferenciasDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "preferencias_foraneogo"
)