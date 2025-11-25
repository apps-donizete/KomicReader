package com.dv.apps.komic.reader.data.repository

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.dv.apps.komic.reader.data.protobuf.copy
import com.dv.apps.komic.reader.data.protobuf.settingsDatastore
import com.dv.apps.komic.reader.domain.model.Settings
import com.dv.apps.komic.reader.domain.repository.SettingsManager
import kotlinx.coroutines.flow.map

class SettingsManagerImpl(
    private val context: Context,
) : SettingsManager {
    override suspend fun addSelectedFolder(path: String) {
        context.contentResolver.takePersistableUriPermission(
            path.toUri(),
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )

        context.settingsDatastore.updateData {
            it.copy { selectedFolders += path }
        }
    }

    override suspend fun setHorizontalPreviewColumnSize(size: Int) {
        context.settingsDatastore.updateData {
            it.copy { horizontalPreviewColumnSize = size }
        }
    }

    override suspend fun setVerticalPreviewColumnSize(size: Int) {
        context.settingsDatastore.updateData {
            it.copy { verticalPreviewColumnSize = size }
        }
    }

    override fun getSettings() = context.settingsDatastore.data.map {
        Settings(
            it.selectedFoldersList,
            it.horizontalPreviewColumnSize,
            it.verticalPreviewColumnSize
        )
    }
}