package com.dv.apps.komic.reader.filesystem.tree

interface VirtualFileTreeManager {
    suspend fun buildTree(paths: List<String>): VirtualFileTree

    fun count(virtualFileTree: VirtualFileTree): Int
}