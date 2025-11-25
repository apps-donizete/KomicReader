package com.dv.apps.komic.reader.domain.repository

import com.dv.apps.komic.reader.domain.model.VirtualFile
import java.io.InputStream

interface VirtualFilesystem {
    fun scanRecursively(path: String): VirtualFile

    fun getInputStream(virtualFile: VirtualFile.File): InputStream?
}