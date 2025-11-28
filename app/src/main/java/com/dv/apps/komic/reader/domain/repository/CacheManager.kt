package com.dv.apps.komic.reader.domain.repository

interface CacheManager {
    suspend fun add(id: Int, path: String): String?

    suspend fun get(id: Int): String?
}