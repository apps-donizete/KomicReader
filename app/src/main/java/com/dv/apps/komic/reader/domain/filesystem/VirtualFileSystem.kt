package com.dv.apps.komic.reader.domain.filesystem

import com.dv.apps.komic.reader.domain.model.Settings

interface VirtualFileSystem {
    suspend fun buildTree(
        path: String,
        quality: Settings.Quality
    ): VirtualFile?
}