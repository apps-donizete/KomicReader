package com.dv.apps.komic.reader.domain.filesystem

import com.dv.apps.komic.reader.domain.model.Settings

sealed interface VirtualFile {
    val name: String
    val path: String

    data class Folder(
        override val name: String,
        override val path: String = "",
        val children: List<VirtualFile> = emptyList()
    ) : VirtualFile

    data class File(
        override val name: String,
        override val path: String = "",
        val type: String = ""
    ) : VirtualFile {

        data class WithThumbnail(
            val file: File,
            val thumbnail: Thumbnail = Thumbnail()
        ) : VirtualFile {
            override val name = file.name

            override val path = file.path
        }
    }

    data class Thumbnail(
        val owner: String = "",
        val path: String = "",
        val width: Int = 0,
        val height: Int = 0,
        val quality: Int = 0
    )
}