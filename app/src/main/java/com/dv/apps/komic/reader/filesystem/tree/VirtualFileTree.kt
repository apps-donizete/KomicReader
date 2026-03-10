package com.dv.apps.komic.reader.filesystem.tree

import com.dv.apps.komic.reader.filesystem.platform.PlatformFile

sealed interface VirtualFileTree {
    val name: String

    data class Folder(
        val platformFile: PlatformFile = PlatformFile(),
        val children: List<VirtualFileTree> = emptyList()
    ) : VirtualFileTree {
        constructor(
            name: String,
            children: List<VirtualFileTree> = emptyList()
        ) : this(
            PlatformFile(name = name),
            children
        )

        override val name get() = platformFile.name
    }

    data class File(
        val platformFile: PlatformFile
    ) : VirtualFileTree {
        constructor(name: String) : this(PlatformFile(name = name))

        override val name get() = platformFile.name

        data class WithThumbnail(
            val virtualFile: File,
            val thumbnail: Thumbnail = Thumbnail()
        ) : VirtualFileTree {
            override val name get() = virtualFile.name
        }
    }

    @JvmInline
    value class Thumbnail(
        val path: String = ""
    )
}