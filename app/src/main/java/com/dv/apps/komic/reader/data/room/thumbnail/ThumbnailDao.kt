package com.dv.apps.komic.reader.data.room.thumbnail

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ThumbnailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(entity: ThumbnailEntity)

    @Query("SELECT * FROM thumbnail WHERE id = :id")
    suspend fun getById(id: Int): ThumbnailEntity?
}