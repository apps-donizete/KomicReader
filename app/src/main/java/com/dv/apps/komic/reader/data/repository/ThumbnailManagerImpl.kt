package com.dv.apps.komic.reader.data.repository

import com.dv.apps.komic.reader.data.room.thumbnail.ThumbnailDao
import com.dv.apps.komic.reader.data.room.thumbnail.ThumbnailEntity
import com.dv.apps.komic.reader.domain.filesystem.VirtualFile
import com.dv.apps.komic.reader.domain.repository.ThumbnailManager

class ThumbnailManagerImpl(
    private val thumbnailDao: ThumbnailDao
) : ThumbnailManager {
    override suspend fun add(
        file: VirtualFile.File,
        thumbnail: VirtualFile.Thumbnail
    ) {
        thumbnailDao.create(thumbnail.run {
            ThumbnailEntity(
                id = 0,
                owner = file.path,
                path = path,
                width = width,
                height = height,
                quality = quality
            )
        })
    }

    override suspend fun get(
        file: VirtualFile.File
    ) = thumbnailDao.get(file.path)?.run {
        VirtualFile.Thumbnail(
            path,
            width,
            height,
            quality
        )
    }
}