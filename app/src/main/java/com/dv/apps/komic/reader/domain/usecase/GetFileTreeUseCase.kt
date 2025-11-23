package com.dv.apps.komic.reader.domain.usecase

import com.dv.apps.komic.reader.domain.file.FileTreeManager
import com.dv.apps.komic.reader.domain.model.FileTree

class GetFileTreeUseCase(
    private val fileTreeManager: FileTreeManager
) : suspend (String) -> FileTree {
    override suspend fun invoke(path: String) = fileTreeManager.getFileTree(path)
}