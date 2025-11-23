package com.dv.apps.komic.reader.feature.shelf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dv.apps.komic.reader.domain.file.DocumentTreeManager
import com.dv.apps.komic.reader.domain.model.FileTree
import com.dv.apps.komic.reader.domain.usecase.GetFileTreeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class State(
    val isLoading: Boolean = false,
    val fileTrees: List<FileTree> = emptyList()
)

sealed interface Intent

class ShelfScreenViewModel(
    private val documentTreeManager: DocumentTreeManager,
    private val getFileTreeUseCase: GetFileTreeUseCase
) : ViewModel() {
    val state = MutableStateFlow(State())

    init {
        viewModelScope.launch {
            documentTreeManager.getDocumentTrees().collect { documentTrees ->
                state.update {
                    it.copy(fileTrees = documentTrees.map { documentTree ->
                        getFileTreeUseCase(documentTree.path)
                    })
                }
            }
        }
    }

    fun handleIntent(intent: Intent) {

    }
}