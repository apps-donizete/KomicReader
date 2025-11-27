package com.dv.apps.komic.reader.ui.thumbnail

import androidx.compose.foundation.layout.aspectRatio
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
import com.dv.apps.komic.reader.R
import com.dv.apps.komic.reader.domain.filesystem.VirtualFile
import com.dv.apps.komic.reader.ui.theme.KomicReaderTheme

@Composable
fun Thumbnail(
    modifier: Modifier = Modifier,
    file: VirtualFile.File
) {
    OutlinedCard(modifier) {
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            model = R.drawable.ic_preview,
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        )
        Text(
            file.name,
            Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Thumbnail(
    modifier: Modifier = Modifier,
    file: VirtualFile.File.WithThumbnail
) {
    OutlinedCard(modifier) {
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            model = file.thumbnail.path,
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        )
        Text(
            file.name,
            Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center
        )
    }
}

@PreviewScreenSizes
@Composable
private fun ThumbnailPreview() {
    KomicReaderTheme {
        LazyVerticalGrid(GridCells.Adaptive(180.dp)) {
            items(100) {
                Thumbnail(
                    Modifier
                        .aspectRatio(9 / 16f)
                        .padding(8.dp),
                    VirtualFile.File.WithThumbnail(
                        VirtualFile.File(
                            "Herry Pouter"
                        )
                    )
                )
            }
        }
    }
}