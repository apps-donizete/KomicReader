package com.dv.apps.komic.reader.data.file

import android.content.Context
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import com.dv.apps.komic.reader.data.room.FileTreeDAO
import com.dv.apps.komic.reader.data.room.FileTreeEntity
import com.dv.apps.komic.reader.domain.file.DocumentTreeManager
import com.dv.apps.komic.reader.domain.model.DocumentTree
import com.dv.apps.komic.reader.ext.mapItems
import java.net.URLDecoder

class DocumentTreeManagerImpl(
    private val context: Context,
    private val fileTreeDAO: FileTreeDAO,
) : DocumentTreeManager {
    override suspend fun addDocumentTree(path: String) {
        fileTreeDAO.create(FileTreeEntity(path = path))

        context.contentResolver.takePersistableUriPermission(
            path.toUri(),
            android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
    }

    override fun getDocumentTrees() = fileTreeDAO.all().mapItems {
        DocumentFile.fromTreeUri(context, it.path.toUri())?.toEntity()
    }
}

private val DECODER: (String) -> String = {
    URLDecoder.decode(it, "UTF-8")
}

private fun DocumentFile.toEntity() = run {
    DocumentTree(
        path = "$uri",
        name = "$uri".run(DECODER).split(":").last()
    )
}