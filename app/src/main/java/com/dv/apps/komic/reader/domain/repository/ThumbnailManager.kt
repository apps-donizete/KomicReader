package com.dv.apps.komic.reader.domain.repository

import com.dv.apps.komic.reader.domain.filesystem.VirtualFile
import com.dv.apps.komic.reader.domain.model.Settings
import com.dv.apps.komic.reader.platform.PlatformFile

interface ThumbnailManager {
    suspend fun get(
        platformFile: PlatformFile,
        quality: Settings.Quality
    ): VirtualFile.Thumbnail?
}