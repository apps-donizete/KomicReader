package com.dv.apps.komic.reader.domain.folder

import kotlinx.coroutines.flow.Flow

interface FolderManager {
    suspend fun saveFolder(path: String)

    fun getFolders(): Flow<List<Folder>>
}