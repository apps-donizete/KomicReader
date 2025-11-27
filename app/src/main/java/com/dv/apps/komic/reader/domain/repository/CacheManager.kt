package com.dv.apps.komic.reader.domain.repository

import java.io.File
import java.io.InputStream

interface CacheManager {
    suspend fun add(
        id: String,
        file: File
    )

    suspend fun add(
        id: String,
        input: InputStream
    )

    suspend fun get(
        id: String
    ): File?

    suspend fun delete(
        id: String
    ): Boolean
}