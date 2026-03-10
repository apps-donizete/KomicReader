package com.dv.apps.komic.reader.domain.repository

import com.dv.apps.komic.reader.filesystem.tree.VirtualFileTree
import com.dv.apps.komic.reader.domain.model.Settings
import com.dv.apps.komic.reader.filesystem.platform.PlatformFile

interface ThumbnailManager {
    suspend fun clear()
    suspend fun generate(platformFile: PlatformFile, quality: Settings.Quality)
    suspend fun get(platformFile: PlatformFile): VirtualFileTree.Thumbnail?
}