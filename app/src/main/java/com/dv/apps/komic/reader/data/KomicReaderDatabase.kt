package com.dv.apps.komic.reader.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dv.apps.komic.reader.data.room.thumbnail.ThumbnailDao
import com.dv.apps.komic.reader.data.room.thumbnail.ThumbnailEntity

@Database(
    entities = [
        ThumbnailEntity::class
    ],
    version = 1
)
abstract class KomicReaderDatabase : RoomDatabase() {
    abstract val thumbnailDAO: ThumbnailDao
}