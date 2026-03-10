package com.dv.apps.komic.reader.domain.usecase

import com.dv.apps.komic.reader.domain.repository.SettingsManager
import com.dv.apps.komic.reader.domain.repository.ThumbnailManager
import com.dv.apps.komic.reader.filesystem.platform.PlatformFile
import com.dv.apps.komic.reader.filesystem.platform.PlatformFileManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.LinkedList

class RefreshThumbnailUseCase(
    private val settingsManager: SettingsManager,
    private val platformFileManager: PlatformFileManager,
    private val thumbnailManager: ThumbnailManager
) : suspend () -> Unit {
    override suspend fun invoke() {
        val settings = settingsManager.getSettings().first()
        val quality = settings.quality
        val platformFiles = settings.selectedFolders.mapNotNull(platformFileManager::get)
        val linkedList = LinkedList(platformFiles)

        withContext(Dispatchers.IO) {
            while (linkedList.isNotEmpty()) {
                val platformFile = linkedList.pop()
                if (platformFile.type is PlatformFile.Type.Folder) {
                    linkedList.addAll(platformFileManager.listFiles(platformFile))
                    continue
                }
                launch { thumbnailManager.generate(platformFile, quality) }
            }
        }
    }
}