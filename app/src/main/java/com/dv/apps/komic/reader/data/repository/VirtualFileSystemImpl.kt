package com.dv.apps.komic.reader.data.repository

import android.content.Context
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import com.dv.apps.komic.reader.domain.filesystem.VirtualFile
import com.dv.apps.komic.reader.domain.filesystem.VirtualFileSystem
import com.dv.apps.komic.reader.domain.model.Settings
import com.dv.apps.komic.reader.domain.repository.CacheManager
import com.dv.apps.komic.reader.domain.repository.ThumbnailManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream

class VirtualFileSystemImpl(
    private val context: Context,
    private val thumbnailManager: ThumbnailManager,
    private val cacheManager: CacheManager,
) : VirtualFileSystem {
    override suspend fun find(
        path: String,
        quality: Settings.Quality
    ): VirtualFile {
        val uri = path.toUri()
        val documentFile = DocumentFile.fromTreeUri(
            context,
            uri
        ) ?: return VirtualFile.Folder("Error", path, emptyList())
        return buildTree(documentFile, quality)
    }

    private suspend fun buildTree(
        documentFile: DocumentFile,
        quality: Settings.Quality
    ): VirtualFile = withContext(Dispatchers.IO) {
        if (documentFile.isDirectory) {
            val children = documentFile
                .listFiles()
                .map { async { buildTree(it, quality) } }

            VirtualFile.Folder(
                documentFile.name.orEmpty(),
                documentFile.uri.toString(),
                children.awaitAll()
            )
        } else {
            val file = VirtualFile.File(
                documentFile.name.orEmpty(),
                documentFile.uri.toString(),
                documentFile.type.orEmpty()
            )

            val thumbnail = thumbnail(file, quality) ?: return@withContext file

            VirtualFile.File.WithThumbnail(file, thumbnail)
        }
    }

    override fun open(file: VirtualFile.File): InputStream? {
        val uri = file.path.toUri()
        return context.contentResolver.openInputStream(uri)
    }

    override suspend fun thumbnail(
        file: VirtualFile.File,
        quality: Settings.Quality
    ): VirtualFile.Thumbnail? {
        val cache = thumbnailManager.get(file)

        if (cache != null && cache.quality == quality.ordinal) return cache

        val temporary = GenerateThumbnail(this, file, quality) ?: return null

        cacheManager.add(file.name, File(temporary.path))

        val finalPath = cacheManager.get(file.name) ?: return null

        val thumbnail = temporary.copy(path = finalPath.absolutePath)

        thumbnailManager.add(file, thumbnail)

        return thumbnail
    }
}