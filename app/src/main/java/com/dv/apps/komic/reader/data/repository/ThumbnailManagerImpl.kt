package com.dv.apps.komic.reader.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import com.dv.apps.komic.reader.domain.model.Settings
import com.dv.apps.komic.reader.domain.repository.FileReader
import com.dv.apps.komic.reader.domain.repository.ThumbnailManager
import com.dv.apps.komic.reader.filesystem.platform.PlatformFile
import com.dv.apps.komic.reader.filesystem.tree.VirtualFileTree
import java.io.File

private const val THUMBNAIL = "thumbnail"

private fun getFactorForQuality(
    quality: Settings.Quality
) = when (quality) {
    Settings.Quality.HD -> 16
    Settings.Quality.FULL_HD -> 8
    Settings.Quality.TWO_K -> 4
    Settings.Quality.FOUR_K -> 1
}

class ThumbnailManagerImpl(
    private val context: Context,
    private val fileReader: FileReader
) : ThumbnailManager {
    private val thumbnailDir = context.getExternalFilesDir(THUMBNAIL)

    override suspend fun clear() {
        thumbnailDir?.deleteRecursively()
    }

    override suspend fun generate(
        platformFile: PlatformFile,
        quality: Settings.Quality
    ) {
        val id = platformFile.descriptor.hashCode()
        val thumbnailFile = File(thumbnailDir, "$id")

        fileReader.open(platformFile)?.use { fileReaderState ->
            if (!fileReaderState.hasNext()) return
            thumbnailFile.outputStream().use { outputStream ->
                fileReaderState.next().readTo(outputStream)
            }
        } ?: return

        val factor = getFactorForQuality(quality)
        val decode = BitmapFactory.decodeFile(thumbnailFile.absolutePath)
        val resized = ThumbnailUtils.extractThumbnail(
            decode,
            decode.width / factor,
            decode.height / factor
        )

        thumbnailFile.outputStream().use {
            resized.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }

        decode.recycle()
        resized.recycle()
    }

    override suspend fun get(
        platformFile: PlatformFile
    ): VirtualFileTree.Thumbnail? {
        val id = platformFile.descriptor.hashCode()
        val thumbnailFile = File(thumbnailDir, "$id")

        if (thumbnailFile.exists().not()) return null

        return VirtualFileTree.Thumbnail(
            thumbnailFile.absolutePath
        )
    }
}