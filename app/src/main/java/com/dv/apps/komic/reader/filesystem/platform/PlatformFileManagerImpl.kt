package com.dv.apps.komic.reader.filesystem.platform

import android.content.Context
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import java.io.InputStream

class PlatformFileManagerImpl(
    private val context: Context
) : PlatformFileManager {
    override fun get(
        descriptor: String
    ) = DocumentFile.fromTreeUri(
        context,
        descriptor.toUri()
    )?.run(DocumentFile::toDomain)

    override fun listFiles(
        file: PlatformFile
    ) = DocumentFile.fromTreeUri(
        context,
        file.descriptor.toUri()
    )
        ?.listFiles()
        ?.sortedBy { it.name }
        ?.map(DocumentFile::toDomain) ?: emptyList()

    override fun open(
        file: PlatformFile
    ): InputStream? {
        val uri = file.descriptor.toUri()
        return context.contentResolver.openInputStream(uri)
    }
}

private fun DocumentFile.toDomain() = PlatformFile(
    "$uri",
    name.orEmpty(),
    if (isDirectory) {
        PlatformFile.Type.Folder
    } else {
        PlatformFile.Type.File(type.orEmpty())
    }
)