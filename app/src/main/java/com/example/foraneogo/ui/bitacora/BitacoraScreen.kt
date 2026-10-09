package com.example.foraneogo.ui.bitacora

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foraneogo.ui.common.ViewModelFactory

@Composable
fun BitacoraScreen(
    onVolver: () -> Unit,
    viewModel: BitacoraViewModel = viewModel(factory = ViewModelFactory.Factory)
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()

    BitacoraContent(
        estado = estado,
        onVolver = onVolver
    )
}
