package com.dv.apps.komic.reader.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import com.dv.apps.komic.reader.data.room.thumbnail.ThumbnailDao
import com.dv.apps.komic.reader.data.room.thumbnail.ThumbnailEntity
import com.dv.apps.komic.reader.domain.filesystem.VirtualFile
import com.dv.apps.komic.reader.domain.model.Settings
import com.dv.apps.komic.reader.domain.repository.CacheManager
import com.dv.apps.komic.reader.domain.repository.ThumbnailManager
import com.dv.apps.komic.reader.platform.PlatformFile
import com.dv.apps.komic.reader.platform.PlatformFileManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.zip.ZipInputStream

class ThumbnailManagerImpl(
    private val platformFileManager: PlatformFileManager,
    private val cacheManager: CacheManager,
    private val thumbnailDao: ThumbnailDao
) : ThumbnailManager {
    private fun getFactorForQuality(
        quality: Settings.Quality
    ) = when (quality) {
        Settings.Quality.HD -> 16
        Settings.Quality.FULL_HD -> 8
        Settings.Quality.TWO_K -> 4
        Settings.Quality.FOUR_K -> 1
    }

    private suspend fun generate(
        id: Int,
        platformFile: PlatformFile,
        quality: Settings.Quality
    ): VirtualFile.Thumbnail? = withContext(Dispatchers.IO) {
        val tmpFile = File.createTempFile("tmp_thumbnail", "")

        platformFileManager.open(platformFile).run(::ZipInputStream).use { zip ->
            if (zip.nextEntry == null) {
                tmpFile.delete()
                return@withContext null
            }
            tmpFile.outputStream().use(zip::copyTo)
        }

        val factor = getFactorForQuality(quality)
        val decode = BitmapFactory.decodeFile(tmpFile.absolutePath)
        val resized = ThumbnailUtils.extractThumbnail(
            decode,
            decode.width / factor,
            decode.height / factor
        )

        tmpFile.outputStream().use {
            resized.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }

        val thumbnail = VirtualFile.Thumbnail(
            id,
            tmpFile.absolutePath,
            resized.width,
            resized.height,
            quality.ordinal
        )

        decode.recycle()
        resized.recycle()

        thumbnail
    }

    override suspend fun get(
        platformFile: PlatformFile,
        quality: Settings.Quality
    ): VirtualFile.Thumbnail? {
        val cacheId = platformFile.descriptor.hashCode()
        val cache = thumbnailDao.getById(cacheId)
        if (cache != null && cache.quality == quality.ordinal) return cache.toDomain()

        // no valid thumbnail in cache, generate new one
        val temporaryThumbnail = generate(
            cacheId,
            platformFile,
            quality
        ) ?: return null

        val cachePath = cacheManager.add(cacheId, temporaryThumbnail.path) ?: return null
        val finalThumbnail = temporaryThumbnail.copy(path = cachePath)

        thumbnailDao.create(finalThumbnail.toEntity())

        return finalThumbnail
    }

    private fun VirtualFile.Thumbnail.toEntity() = ThumbnailEntity(
        id,
        path,
        width,
        height,
        quality
    )

    private fun ThumbnailEntity.toDomain() = VirtualFile.Thumbnail(
        id,
        path,
        width,
        height,
        quality
    )
}