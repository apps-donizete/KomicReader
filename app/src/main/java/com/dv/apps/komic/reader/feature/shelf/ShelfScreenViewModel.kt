package com.dv.apps.komic.reader.feature.shelf

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

data class State(
    val isLoading: Boolean = false
)

sealed interface Intent

class ShelfScreenViewModel : ViewModel() {
    val state = MutableStateFlow(State())

    fun handleIntent(intent: Intent) {

    }
}