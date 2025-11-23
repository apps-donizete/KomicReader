package com.dv.apps.komic.reader.domain.model

sealed interface FileTree {
    val name: String
    val path: String

    object Empty : FileTree {
        override val name = ""
        override val path = ""
    }

    class Folder(
        override val name: String,
        override val path: String,
        val children: List<FileTree>
    ) : FileTree

    class File(
        override val name: String,
        override val path: String,
        val type: String
    ) : FileTree
}