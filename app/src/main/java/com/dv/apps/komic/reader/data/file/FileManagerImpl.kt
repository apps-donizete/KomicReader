package com.dv.apps.komic.reader.data.file

import android.content.Context
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import com.dv.apps.komic.reader.data.room.FileDAO
import com.dv.apps.komic.reader.data.room.FileEntity
import com.dv.apps.komic.reader.domain.file.File
import com.dv.apps.komic.reader.domain.file.FileManager
import com.dv.apps.komic.reader.ext.mapItems
import java.net.URLDecoder

class FileManagerImpl(
    private val context: Context,
    private val fileDAO: FileDAO,
) : FileManager {
    override suspend fun saveFile(path: String) {
        fileDAO.create(FileEntity(path = path))

        context.contentResolver.takePersistableUriPermission(
            path.toUri(),
            android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
    }

    override fun getFiles() = fileDAO.all().mapItems {
        DocumentFile.fromSingleUri(
            context,
            it.path.toUri()
        )?.toEntity()
    }

    override suspend fun getFiles(
        file: File
    ): List<File> {
        val tree = DocumentFile.fromTreeUri(context, file.path.toUri())
        val files = tree?.listFiles() ?: emptyArray()
        return files.map(DocumentFile::toEntity)
    }
}

private val DECODER: (String) -> String = {
    URLDecoder.decode(it, "UTF-8")
}

private fun DocumentFile.toEntity() = run {
    File(
        path = uri.toString().run(DECODER),
        name = name.orEmpty()
    )
}