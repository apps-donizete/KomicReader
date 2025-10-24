package com.dv.apps.komic.reader.feature.settings.folder

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dv.apps.komic.reader.domain.file.File
import com.dv.apps.komic.reader.domain.file.FileManager
import com.dv.apps.komic.reader.ext.collectInto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class State(
    val isLoading: Boolean = false,
    val noSelection: Boolean = false,
    val selectedFolders: List<File> = emptyList()
) {
    fun copyWithSelectedFolders(
        selectedFolders: List<File>
    ) = copy(selectedFolders = selectedFolders)
}

sealed interface Intent {
    data class OnFileTreeSelected(val uri: Uri?) : Intent
}

class FolderSourceSettingsSectionViewModel(
    private val fileManager: FileManager
) : ViewModel() {
    val state = MutableStateFlow(State())

    init {
        viewModelScope.launch {
            launch {
                fileManager
                    .getFiles()
                    .collectInto(state, State::copyWithSelectedFolders)
            }
        }
    }

    fun handleIntent(intent: Intent) {
        when (intent) {
            is Intent.OnFileTreeSelected -> onFileTreeSelected(intent.uri)
        }
    }

    private fun onFileTreeSelected(uri: Uri?) {
        if (uri == null) {
            state.update { it.copy(noSelection = true) }
            return
        }
        viewModelScope.launch {
            fileManager.saveFile(uri.toString())
        }
    }
}