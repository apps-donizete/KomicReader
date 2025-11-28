package com.dv.apps.komic.reader.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dv.apps.komic.reader.feature.home.components.HomeHeader
import com.dv.apps.komic.reader.feature.home.components.NowReadingSection
import com.dv.apps.komic.reader.ui.theme.KomicReaderTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        GridCells.Fixed(1),
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item { Spacer(Modifier.windowInsetsPadding(WindowInsets.statusBars)) }

        item {
            HomeHeader()
        }

        item {
            NowReadingSection()
        }

        item { Spacer(Modifier.windowInsetsPadding(WindowInsets.navigationBars)) }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    KomicReaderTheme {
        HomeScreen()
    }
}