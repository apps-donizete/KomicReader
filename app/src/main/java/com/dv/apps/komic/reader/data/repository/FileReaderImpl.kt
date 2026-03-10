package com.dv.apps.komic.reader.data.repository

import com.dv.apps.komic.reader.filesystem.tree.VirtualFileTree
import com.dv.apps.komic.reader.domain.repository.FileReader
import com.dv.apps.komic.reader.filesystem.platform.PlatformFile
import com.dv.apps.komic.reader.filesystem.platform.PlatformFileManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream
import java.util.zip.ZipInputStream

class FileReaderImpl(
    private val platformFileManager: PlatformFileManager
) : FileReader {
    override fun open(
        virtualFileTree: VirtualFileTree.File
    ) = open(virtualFileTree.platformFile)

    override fun open(platformFile: PlatformFile): FileReader.State? {
        val inputStream = platformFileManager.open(platformFile) ?: return null
        return ZipState(inputStream)
    }

    class ZipState(
        private val inputStream: InputStream
    ) : FileReader.State {
        private val zipInputStream = ZipInputStream(inputStream)

        override fun next() = object : FileReader.Readable {
            override suspend fun readTo(
                outputStream: OutputStream
            ): Unit = withContext(Dispatchers.IO) {
                zipInputStream.copyTo(outputStream)
            }
        }

        override fun hasNext() =
            zipInputStream.nextEntry != null

        override fun close() {
            zipInputStream.close()
            inputStream.close()
        }
    }
}