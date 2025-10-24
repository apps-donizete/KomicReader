package com.dv.apps.komic.reader.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dv.apps.komic.reader.data.room.FileDAO
import com.dv.apps.komic.reader.data.room.FileEntity

@Database(
    entities = [
        FileEntity::class
    ],
    version = 1
)
abstract class KomicReaderDatabase : RoomDatabase() {
    abstract val fileDAO: FileDAO
}