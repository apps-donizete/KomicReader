package com.dv.apps.komic.reader.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FileDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun create(entity: FileEntity)

    @Query("SELECT * FROM files")
    fun all(): Flow<List<FileEntity>>
}