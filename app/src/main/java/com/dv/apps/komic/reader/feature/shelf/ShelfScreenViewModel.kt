package com.dv.apps.komic.reader.feature.shelf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dv.apps.komic.reader.domain.model.KomicPreviewTree
import com.dv.apps.komic.reader.domain.model.Settings
import com.dv.apps.komic.reader.domain.repository.SettingsManager
import com.dv.apps.komic.reader.domain.repository.VirtualFilesystem
import com.dv.apps.komic.reader.domain.usecase.GetKomicPreviewTree
import com.dv.apps.komic.reader.ext.mapItems
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.zip

data class State(
    val komicPreviewTrees: List<KomicPreviewTree> = emptyList(),
    val verticalPreviewColumnSize: Int = 0,
    val horizontalPreviewColumnSize: Int = 0,
    val isLoading: Boolean = false
)

sealed interface Intent

class ShelfScreenViewModel(
    private val settingsManager: SettingsManager,
    private val virtualFilesystem: VirtualFilesystem,
    private val getKomicPreviewTree: GetKomicPreviewTree
) : ViewModel() {
    val state = settingsManager
        .getSettings().map { it.selectedFolders }
        .mapItems(virtualFilesystem::scanRecursively)
        .mapItems(getKomicPreviewTree)
        .combine(settingsManager.getSettings()) { preview, settings ->
            State(
                preview,
                settings.verticalPreviewColumnSize,
                settings.horizontalPreviewColumnSize
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            State(isLoading = true)
        )

    fun handleIntent(intent: Intent) {

    }
}