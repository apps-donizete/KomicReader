package com.dv.apps.komic.reader.data.room.thumbnail

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("thumbnail")
data class ThumbnailEntity(
    @PrimaryKey
    val id: Int = 0,
    val path: String,
    val width: Int,
    val height: Int,
    val quality: Int
)