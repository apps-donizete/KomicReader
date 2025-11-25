package com.dv.apps.komic.reader.domain.model

data class Settings(
    val selectedFolders: List<String>,
    val verticalPreviewSpanSize: Int,
    val horizontalPreviewSpanSize: Int,
    val quality: Quality
) {
    enum class Quality {
        HD, FULL_HD, TWO_K, FOUR_K
    }
}