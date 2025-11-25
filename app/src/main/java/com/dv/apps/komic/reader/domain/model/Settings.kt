package com.dv.apps.komic.reader.domain.model

data class Settings(
    val selectedFolders: List<String>,
    val verticalPreviewColumnSize: Int,
    val horizontalPreviewColumnSize: Int,
)