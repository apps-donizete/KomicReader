package com.dv.apps.komic.reader.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dv.apps.komic.reader.data.folder.FolderDAO
import com.dv.apps.komic.reader.data.folder.FolderEntity

@Database(
    entities = [
        FolderEntity::class
    ],
    version = 1
)
abstract class KomicReaderDatabase : RoomDatabase() {
    abstract val folderDAO: FolderDAO
}