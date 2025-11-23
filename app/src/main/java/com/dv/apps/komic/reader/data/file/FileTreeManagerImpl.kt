package com.dv.apps.komic.reader.data.file

import android.content.Context
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import com.dv.apps.komic.reader.domain.file.FileTreeManager
import com.dv.apps.komic.reader.domain.model.FileTree

class FileTreeManagerImpl(
    private val context: Context
) : FileTreeManager {
    override fun getFileTree(
        path: String
    ): FileTree {
        val uri = path.toUri()
        val documentFile = DocumentFile.fromTreeUri(context, uri) ?: return FileTree.Empty
        return buildFileTree(documentFile)
    }

    private fun buildFileTree(documentFile: DocumentFile): FileTree {
        return if (documentFile.isDirectory) {
            val children = documentFile.listFiles().map(::buildFileTree)
            FileTree.Folder(
                documentFile.name.orEmpty(),
                documentFile.uri.toString(),
                children
            )
        } else {
            FileTree.File(
                documentFile.name.orEmpty(),
                documentFile.uri.toString(),
                documentFile.type.orEmpty()
            )
        }
    }
}