package com.dv.apps.komic.reader.filesystem.platform

data class PlatformFile(
    val descriptor: String = "",
    val name: String = "",
    val type: Type = Type.Folder
) {
    sealed interface Type {
        data object Folder : Type

        data class File(
            val mimeType: String
        ) : Type
    }
}