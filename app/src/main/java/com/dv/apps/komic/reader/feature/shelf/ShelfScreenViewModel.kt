package com.dv.apps.komic.reader.feature.shelf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dv.apps.komic.reader.domain.file.DocumentTreeManager
import com.dv.apps.komic.reader.domain.model.FileTree
import com.dv.apps.komic.reader.domain.usecase.GetFileTreeUseCase
import com.dv.apps.komic.reader.ext.mapItems
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class State(
    val isLoading: Boolean = true,
    val fileTrees: List<FileTree> = emptyList()
)

sealed interface Intent

class ShelfScreenViewModel(
    private val documentTreeManager: DocumentTreeManager,
    private val getFileTreeUseCase: GetFileTreeUseCase
) : ViewModel() {
    val state = documentTreeManager.getDocumentTrees().mapItems {
        getFileTreeUseCase(it.path)
    }.map {
        State(isLoading = false, fileTrees = it)
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        State()
    )

    fun handleIntent(intent: Intent) {

    }
}