package com.dv.apps.komic.reader.domain.model

data class Settings(
    val selectedFolders: List<String> = emptyList(),
    val verticalPreviewSpanSize: Int = 0,
    val horizontalPreviewSpanSize: Int = 0,
    val quality: Quality = Quality.HD
) {
    enum class Quality {
        HD, FULL_HD, TWO_K, FOUR_K
    }
}