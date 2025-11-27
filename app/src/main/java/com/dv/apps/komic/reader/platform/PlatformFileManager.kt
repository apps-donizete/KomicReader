package com.dv.apps.komic.reader.platform

import java.io.InputStream

interface PlatformFileManager {
    fun get(descriptor: String): PlatformFile?

    fun listFiles(file: PlatformFile): List<PlatformFile>

    fun open(file: PlatformFile): InputStream?
}