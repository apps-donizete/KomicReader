package com.dv.apps.komic.reader.domain.file

import kotlinx.coroutines.flow.Flow

interface FileManager {
    suspend fun saveFile(path: String)
    fun getFiles(): Flow<List<File>>
    suspend fun getFiles(file: File): List<File>
}