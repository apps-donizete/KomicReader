package com.dv.apps.komic.reader.data.folder

import android.content.Context
import androidx.core.net.toUri
import com.dv.apps.komic.reader.domain.folder.Folder
import com.dv.apps.komic.reader.domain.folder.FolderManager
import com.dv.apps.komic.reader.ext.mapItems

class FolderManagerImpl(
    private val context: Context,
    private val folderDAO: FolderDAO,
) : FolderManager {
    override suspend fun saveFolder(path: String) {
        folderDAO.create(
            FolderEntity(path = path)
        )

        context.contentResolver.takePersistableUriPermission(
            path.toUri(),
            android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
    }

    override fun getFolders() = folderDAO
        .all()
        .mapItems {
            Folder(it.id, it.path)
        }
}