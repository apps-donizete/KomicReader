package com.dv.apps.komic.reader.data.repository

import android.content.Context
import com.dv.apps.komic.reader.domain.repository.ThumbnailManager
import com.dv.apps.komic.reader.domain.model.VirtualFile
import com.dv.apps.komic.reader.domain.repository.VirtualFilesystem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.zip.ZipInputStream

class ThumbnailManagerImpl(
    private val context: Context,
    private val virtualFilesystem: VirtualFilesystem
) : ThumbnailManager {
    private val thumbnailDir = context
        .getExternalFilesDir("thumbnail")
        ?.also(File::mkdirs)

    override suspend fun getOrCache(
        virtualFile: VirtualFile.File
    ): File? = withContext(Dispatchers.IO) {
        val thumbnailFile = File(thumbnailDir, virtualFile.name)

        if (thumbnailFile.exists() && thumbnailFile.length() > 0) {
            return@withContext thumbnailFile
        }

        thumbnailFile.delete()
        if (!thumbnailFile.createNewFile()) return@withContext null

        virtualFilesystem.getInputStream(virtualFile).run(::ZipInputStream).use { zip ->
            if (zip.nextEntry == null) return@withContext null
            thumbnailFile.outputStream().use(zip::copyTo)
        }

        thumbnailFile
    }

    override suspend fun getOrCache(virtualFile: VirtualFile.Folder): File? {
        return null
    }
}