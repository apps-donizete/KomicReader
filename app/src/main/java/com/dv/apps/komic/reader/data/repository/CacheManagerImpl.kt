package com.dv.apps.komic.reader.data.repository

import android.content.Context
import com.dv.apps.komic.reader.domain.repository.CacheManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream

class CacheManagerImpl(
    private val context: Context
) : CacheManager {
    override suspend fun add(
        id: String,
        file: File
    ): Unit = withContext(Dispatchers.IO) {
        file.inputStream().use {
            context.openFileOutput(
                id,
                Context.MODE_PRIVATE
            ).use(it::copyTo)
        }
    }

    override suspend fun add(
        id: String,
        input: InputStream
    ): Unit = withContext(Dispatchers.IO) {
        context.openFileOutput(
            id,
            Context.MODE_PRIVATE
        ).use(input::copyTo)
    }

    override suspend fun get(
        id: String
    ): File? = context.getFileStreamPath(id).takeIf { it.exists() && it.length() > 0 }

    override suspend fun delete(
        id: String
    ) = get(id)?.run { exists() && delete() } ?: false
}