package com.dv.apps.komic.reader.domain.usecase

import com.dv.apps.komic.reader.domain.model.Settings
import com.dv.apps.komic.reader.domain.repository.SettingsManager
import com.dv.apps.komic.reader.domain.repository.ThumbnailManager
import com.dv.apps.komic.reader.filesystem.platform.PlatformFile
import com.dv.apps.komic.reader.filesystem.platform.PlatformFileManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class RefreshThumbnailUseCase(
    private val settingsManager: SettingsManager,
    private val platformFileManager: PlatformFileManager,
    private val thumbnailManager: ThumbnailManager
) : suspend () -> Unit {
    override suspend fun invoke() = withContext(Dispatchers.IO) {
        val settings = settingsManager.getSettings().first()
        val quality = settings.quality
        val selectedFolders = settings.selectedFolders

        for (selectedFolder in selectedFolders) {
            val platformFile = platformFileManager.get(selectedFolder) ?: continue
            generateThumbnail(platformFile, quality)
        }
    }

    //TODO(improve async here)
    private suspend fun generateThumbnail(
        platformFile: PlatformFile,
        quality: Settings.Quality
    ) {
        if (platformFile.type is PlatformFile.Type.File) {
            thumbnailManager.generate(platformFile, quality)
            return
        }
        for (platformFile in platformFileManager.listFiles(platformFile)) {
            generateThumbnail(platformFile, quality)
        }
    }
}