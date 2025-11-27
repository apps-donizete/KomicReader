package com.dv.apps.komic.reader.data.repository

import com.dv.apps.komic.reader.domain.filesystem.VirtualFile
import com.dv.apps.komic.reader.domain.filesystem.VirtualFileSystem
import com.dv.apps.komic.reader.domain.model.Settings
import com.dv.apps.komic.reader.platform.PlatformFile
import com.dv.apps.komic.reader.platform.PlatformFileManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class VirtualFileSystemImpl(
    private val platformFileManager: PlatformFileManager
) : VirtualFileSystem {
    override suspend fun buildTree(
        path: String,
        quality: Settings.Quality
    ): VirtualFile? {
        val platformFile = platformFileManager.get(path) ?: return null
        if (platformFile.type != PlatformFile.Type.FOLDER) return null
        return buildTree(platformFile)
    }

    private suspend fun buildTree(
        platformFile: PlatformFile
    ): VirtualFile = withContext(Dispatchers.IO) {
        when (platformFile.type) {
            PlatformFile.Type.FILE -> VirtualFile.File(
                platformFile.name,
                platformFile.descriptor,
                platformFile.mimeType
            )

            PlatformFile.Type.FOLDER -> {
                val children = platformFileManager
                    .listFiles(platformFile)
                    .map {
                        async {
                            buildTree(it)
                        }
                    }
                VirtualFile.Folder(
                    platformFile.name,
                    platformFile.descriptor,
                    children.awaitAll()
                )
            }
        }
    }

    override fun count(
        virtualFile: VirtualFile
    ): Int = when (virtualFile) {
        is VirtualFile.File -> 1
        is VirtualFile.File.WithThumbnail -> 1
        is VirtualFile.Folder -> virtualFile.children.sumOf(::count)
    }
}