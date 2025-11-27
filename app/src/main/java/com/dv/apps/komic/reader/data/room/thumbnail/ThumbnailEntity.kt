package com.dv.apps.komic.reader.data.room.thumbnail

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    "thumbnail",
    indices = [
        Index("owner", unique = true)
    ]
)
data class ThumbnailEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val owner: String,
    val path: String,
    val width: Int,
    val height: Int,
    val quality: Int
)