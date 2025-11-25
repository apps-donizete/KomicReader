package com.dv.apps.komic.reader.domain.repository

import com.dv.apps.komic.reader.domain.model.VirtualFile
import java.io.File

interface ThumbnailManager {
    suspend fun removeAllCache()

    suspend fun getOrCache(virtualFile: VirtualFile.File): File?

    suspend fun getOrCache(virtualFile: VirtualFile.Folder): File?
}