package com.dv.apps.komic.reader.domain.usecase

import com.dv.apps.komic.reader.domain.model.KomicPreviewTree
import com.dv.apps.komic.reader.domain.repository.ThumbnailManager
import com.dv.apps.komic.reader.domain.model.VirtualFile

class GetKomicPreviewTree(
    private val thumbnailManager: ThumbnailManager
) : suspend (VirtualFile) -> KomicPreviewTree {
    override suspend fun invoke(
        virtualFile: VirtualFile
    ) = when (virtualFile) {
        is VirtualFile.File -> getPreviewTree(virtualFile)
        is VirtualFile.Folder -> getPreviewTree(virtualFile)
    }

    private suspend fun getPreviewTree(file: VirtualFile.File): KomicPreviewTree {
        val image = thumbnailManager.getOrCache(file)
        return KomicPreviewTree.Done(file.name, image)
    }

    private suspend fun getPreviewTree(file: VirtualFile.Folder): KomicPreviewTree {
        val children = file.children.map { invoke(it) }
        return KomicPreviewTree.Nested(file.name, children)
    }
}