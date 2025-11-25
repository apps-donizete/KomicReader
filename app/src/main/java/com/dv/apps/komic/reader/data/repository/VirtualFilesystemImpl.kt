package com.dv.apps.komic.reader.data.repository

import android.content.Context
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import com.dv.apps.komic.reader.domain.model.VirtualFile
import com.dv.apps.komic.reader.domain.repository.VirtualFilesystem
import java.io.InputStream

class VirtualFilesystemImpl(
    private val context: Context
) : VirtualFilesystem {
    override fun scanRecursively(
        path: String
    ): VirtualFile {
        val uri = path.toUri()
        val documentFile = DocumentFile.fromTreeUri(
            context,
            uri
        ) ?: return VirtualFile.Folder("Error", path, emptyList())
        return buildFileTree(documentFile)
    }

    private fun buildFileTree(documentFile: DocumentFile): VirtualFile {
        return if (documentFile.isDirectory) {
            val children = documentFile
                .listFiles()
                .map(::buildFileTree)
                .sortedBy { it.name }

            VirtualFile.Folder(
                documentFile.name.orEmpty(),
                documentFile.uri.toString(),
                children
            )
        } else {
            VirtualFile.File(
                documentFile.name.orEmpty(),
                documentFile.uri.toString(),
                documentFile.type.orEmpty()
            )
        }
    }

    override fun getInputStream(virtualFile: VirtualFile.File): InputStream? {
        val uri = virtualFile.path.toUri()
        return context.contentResolver.openInputStream(uri)
    }
}