package com.dv.apps.komic.reader.platform

import android.content.Context
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import java.io.InputStream

class PlatformFileManagerImpl(
    private val context: Context
) : PlatformFileManager {
    override fun listFiles(
        file: PlatformFile
    ) = DocumentFile.fromTreeUri(
        context,
        file.descriptor.toUri()
    )
        ?.listFiles()
        ?.map {
            PlatformFile(
                "${it.uri}",
                if (it.isDirectory) {
                    PlatformFile.Type.FOLDER
                } else {
                    PlatformFile.Type.FILE
                },
                it.name.orEmpty()
            )
        } ?: emptyList()

    override fun open(
        file: PlatformFile
    ): InputStream? {
        val uri = file.descriptor.toUri()
        return context.contentResolver.openInputStream(uri)
    }
}