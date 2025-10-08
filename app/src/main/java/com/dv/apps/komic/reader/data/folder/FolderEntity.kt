package com.dv.apps.komic.reader.data.folder

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    "folders",
    indices = [
        Index("path", unique = true)
    ]
)
data class FolderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val path: String
)