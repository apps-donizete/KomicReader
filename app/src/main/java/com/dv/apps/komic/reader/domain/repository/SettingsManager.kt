package com.dv.apps.komic.reader.domain.repository

import com.dv.apps.komic.reader.domain.model.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsManager {
    suspend fun addSelectedFolder(path: String)
    suspend fun setVerticalPreviewSpanSize(size: Int)
    suspend fun setHorizontalPreviewSpanSize(size: Int)
    suspend fun setQuality(quality: Settings.Quality)

    fun getSettings(): Flow<Settings>
}