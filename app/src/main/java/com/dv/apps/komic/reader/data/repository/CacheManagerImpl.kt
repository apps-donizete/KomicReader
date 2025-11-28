package com.dv.apps.komic.reader.data.repository

import android.content.Context
import com.dv.apps.komic.reader.domain.repository.CacheManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class CacheManagerImpl(
    private val context: Context
) : CacheManager {
    override suspend fun add(
        id: Int,
        path: String
    ) = withContext(Dispatchers.IO) {
        File(path).inputStream().use {
            context.openFileOutput(
                "$id",
                Context.MODE_PRIVATE
            ).use(it::copyTo)
        }
        get(id)
    }

    override suspend fun get(
        id: Int
    ) = context
        .getFileStreamPath("$id")
        .takeIf { it.exists() && it.length() > 0 }
        ?.absolutePath
}