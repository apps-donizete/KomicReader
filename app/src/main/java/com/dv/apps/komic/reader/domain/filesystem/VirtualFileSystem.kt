package com.dv.apps.komic.reader.domain.filesystem

import com.dv.apps.komic.reader.domain.model.Settings

interface VirtualFileSystem {
    suspend fun buildTree(
        paths: List<String>,
        quality: Settings.Quality
    ): VirtualFile

    fun count(
        virtualFile: VirtualFile
    ): Int
}