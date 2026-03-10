package com.dv.apps.komic.reader.data.repository

import com.dv.apps.komic.reader.domain.repository.ThumbnailManager
import com.dv.apps.komic.reader.filesystem.platform.PlatformFile
import com.dv.apps.komic.reader.filesystem.platform.PlatformFileManager
import com.dv.apps.komic.reader.filesystem.tree.VirtualFileTree
import com.dv.apps.komic.reader.filesystem.tree.VirtualFileTreeManager

class VirtualFileTreeManagerImpl(
    private val platformFileManager: PlatformFileManager,
    private val thumbnailManager: ThumbnailManager
) : VirtualFileTreeManager {
    override suspend fun buildTree(
        paths: List<String>
    ) = paths
        .mapNotNull(platformFileManager::get)
        .let { platformFiles ->
            VirtualFileTree.Folder(
                PlatformFile(),
                platformFiles.map { platformFile ->
                    buildTree(platformFile)
                }
            )
        }

    private suspend fun buildTree(
        platformFile: PlatformFile
    ) = when (platformFile.type) {
        is PlatformFile.Type.File -> buildFile(platformFile)

        PlatformFile.Type.Folder -> buildFolder(platformFile)
    }

    private suspend fun buildFile(
        platformFile: PlatformFile
    ) = when (val thumbnail = thumbnailManager.get(platformFile)) {
        null -> VirtualFileTree.File(platformFile)
        else -> VirtualFileTree.File.WithThumbnail(
            VirtualFileTree.File(platformFile),
            thumbnail
        )
    }

    private suspend fun buildFolder(
        platformFile: PlatformFile
    ): VirtualFileTree.Folder = platformFileManager
        .listFiles(platformFile)
        .map { platformFile ->
            buildTree(platformFile)
        }
        .let { virtualFileTrees ->
            VirtualFileTree.Folder(
                platformFile,
                virtualFileTrees
            )
        }

    override fun count(
        virtualFileTree: VirtualFileTree
    ): Int = when (virtualFileTree) {
        is VirtualFileTree.File -> 1
        is VirtualFileTree.File.WithThumbnail -> 1
        is VirtualFileTree.Folder -> virtualFileTree.children.sumOf(::count)
    }
}