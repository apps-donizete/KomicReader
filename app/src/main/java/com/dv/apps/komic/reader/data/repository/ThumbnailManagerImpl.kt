package com.dv.apps.komic.reader.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import com.dv.apps.komic.reader.domain.model.Settings
import com.dv.apps.komic.reader.domain.model.VirtualFile
import com.dv.apps.komic.reader.domain.repository.SettingsManager
import com.dv.apps.komic.reader.domain.repository.ThumbnailManager
import com.dv.apps.komic.reader.domain.repository.VirtualFilesystem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.File
import java.util.zip.ZipInputStream

class ThumbnailManagerImpl(
    private val context: Context,
    private val settingsManager: SettingsManager,
    private val virtualFilesystem: VirtualFilesystem
) : ThumbnailManager {
    private val thumbnailDir = context
        .getExternalFilesDir("thumbnail")
        ?.also(File::mkdirs)

    private fun getFactorForQuality(
        quality: Settings.Quality
    ) = when (quality) {
        Settings.Quality.HD -> 16
        Settings.Quality.FULL_HD -> 8
        Settings.Quality.TWO_K -> 4
        Settings.Quality.FOUR_K -> 1
    }

    override suspend fun removeAllCache(): Unit = withContext(Dispatchers.IO) {
        thumbnailDir?.deleteRecursively()
        thumbnailDir?.mkdirs()
    }

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

        val factor = getFactorForQuality(
            settingsManager.getSettings().map { it.quality }.first()
        )
        val bitmap = BitmapFactory.decodeFile(thumbnailFile.absolutePath)
        val bitmapThumbnail = ThumbnailUtils.extractThumbnail(
            bitmap,
            bitmap.width / factor,
            bitmap.height / factor
        )

        thumbnailFile.outputStream().use {
            bitmapThumbnail.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }

        bitmap.recycle()
        bitmapThumbnail.recycle()

        thumbnailFile
    }

    override suspend fun getOrCache(virtualFile: VirtualFile.Folder): File? {
        return null
    }
}