package com.example.foraneogo.ui.bitacora

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foraneogo.ui.common.ViewModelFactory

@Composable
fun BitacoraScreen(
    onVolver: () -> Unit,
    viewModel: BitacoraViewModel = viewModel(factory = ViewModelFactory.Factory)
) {
    val estado by viewModel.estado.collectAsState()

    BitacoraContent(
        estado = estado,
        onVolver = onVolver
    )
}
