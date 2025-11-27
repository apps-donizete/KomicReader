package com.dv.apps.komic.reader.platform

data class PlatformFile(
    val descriptor: String,
    val type: Type,
    val name: String
) {
    enum class Type {
        FILE,
        FOLDER
    }
}