package com.dv.apps.komic.reader.domain.filesystem

import com.dv.apps.komic.reader.domain.model.Settings
import java.io.InputStream

interface VirtualFileSystem {
    suspend fun find(
        path: String,
        quality: Settings.Quality
    ): VirtualFile

    fun open(
        file: VirtualFile.File
    ): InputStream?
}