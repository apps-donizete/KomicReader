package com.dv.apps.komic.reader.platform

data class PlatformFile(
    val descriptor: String,
    val type: Type,
    val name: String,
    val mimeType: String
) {
    enum class Type {
        FILE,
        FOLDER
    }
}