package com.dv.apps.komic.reader.domain.file

import com.dv.apps.komic.reader.domain.model.FileTree

interface FileTreeManager {
    fun getFileTree(path: String): FileTree
}