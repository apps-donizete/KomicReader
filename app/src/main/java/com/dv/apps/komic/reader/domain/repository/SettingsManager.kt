package com.dv.apps.komic.reader.domain.repository

import com.dv.apps.komic.reader.domain.model.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsManager {
    suspend fun addSelectedFolder(path: String)
    suspend fun setHorizontalPreviewColumnSize(size: Int)
    suspend fun setVerticalPreviewColumnSize(size: Int)

    fun getSettings(): Flow<Settings>
}