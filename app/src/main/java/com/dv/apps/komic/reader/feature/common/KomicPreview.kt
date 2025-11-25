package com.dv.apps.komic.reader.feature.common

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.dv.apps.komic.reader.ui.theme.KomicReaderTheme
import java.io.File

@Composable
fun KomicPreview(
    modifier: Modifier = Modifier,
    title: String,
    preview: File? = null
) {
    OutlinedCard(modifier) {
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            model = preview,
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        )
        Text(
            title,
            Modifier.padding(8.dp).fillMaxWidth(),
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center
        )
    }
}

@PreviewScreenSizes
@Composable
private fun KomicPreviewPreview() {
    KomicReaderTheme {
        LazyVerticalGrid(GridCells.Adaptive(180.dp)) {
            items(100) {
                KomicPreview(
                    Modifier
                        .aspectRatio(9 / 16f)
                        .padding(8.dp),
                    "Title number $it"
                )
            }
        }
    }
}