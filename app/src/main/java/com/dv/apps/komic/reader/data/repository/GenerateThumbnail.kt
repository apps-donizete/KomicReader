package com.dv.apps.komic.reader.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import com.dv.apps.komic.reader.domain.filesystem.VirtualFile
import com.dv.apps.komic.reader.domain.filesystem.VirtualFileSystem
import com.dv.apps.komic.reader.domain.model.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.zip.ZipInputStream

object GenerateThumbnail {
    private fun getFactorForQuality(
        quality: Settings.Quality
    ) = when (quality) {
        Settings.Quality.HD -> 16
        Settings.Quality.FULL_HD -> 8
        Settings.Quality.TWO_K -> 4
        Settings.Quality.FOUR_K -> 1
    }

    suspend operator fun invoke(
        virtualFilesystem: VirtualFileSystem,
        file: VirtualFile.File,
        quality: Settings.Quality
    ) = withContext(Dispatchers.IO) {
        val tmpFile = File.createTempFile("tmp_thumbnail", "")

        virtualFilesystem.open(file).run(::ZipInputStream).use { zip ->
            if (zip.nextEntry == null) return@withContext null
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
            tmpFile.absolutePath,
            resized.width,
            resized.height,
            quality.ordinal
        )

        decode.recycle()
        resized.recycle()

        thumbnail
    }
}