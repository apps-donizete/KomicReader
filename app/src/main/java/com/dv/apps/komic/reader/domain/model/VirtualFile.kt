package com.dv.apps.komic.reader.domain.model

sealed interface VirtualFile {
    val name: String
    val path: String

    class Folder(
        override val name: String,
        override val path: String,
        val children: List<VirtualFile>
    ) : VirtualFile

    class File(
        override val name: String,
        override val path: String,
        val type: String
    ) : VirtualFile
}