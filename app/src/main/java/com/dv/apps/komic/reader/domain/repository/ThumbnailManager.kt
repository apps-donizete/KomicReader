package com.dv.apps.komic.reader.domain.repository

import com.dv.apps.komic.reader.domain.filesystem.VirtualFile

interface ThumbnailManager {
    suspend fun add(file: VirtualFile.File, thumbnail: VirtualFile.Thumbnail)

    suspend fun get(file: VirtualFile.File): VirtualFile.Thumbnail?
}