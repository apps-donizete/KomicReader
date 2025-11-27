package com.dv.apps.komic.reader.feature.shelf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dv.apps.komic.reader.domain.filesystem.VirtualFile
import com.dv.apps.komic.reader.domain.filesystem.VirtualFileSystem
import com.dv.apps.komic.reader.domain.model.Settings
import com.dv.apps.komic.reader.domain.repository.SettingsManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class State(
    val trees: List<VirtualFile> = emptyList(),
    val settings: Settings = Settings(),
    val isLoading: Boolean = false
)

sealed interface Intent

class ShelfScreenViewModel(
    private val settingsManager: SettingsManager,
    private val virtualFilesystem: VirtualFileSystem
) : ViewModel() {
    val state = settingsManager
        .getSettings().map { settings ->
            val trees = coroutineScope {
                settings.selectedFolders.map {
                    async {
                        virtualFilesystem.find(
                            it,
                            settings.quality
                        )
                    }
                }
            }.awaitAll()
            State(trees, settings)
        }
        .flowOn(Dispatchers.Default)
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            State(isLoading = true)
        )

    fun handleIntent(intent: Intent) {

    }
}