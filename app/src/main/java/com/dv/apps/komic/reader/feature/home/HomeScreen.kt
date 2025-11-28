package com.dv.apps.komic.reader.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.dv.apps.komic.reader.feature.home.components.HomeHeader
import com.dv.apps.komic.reader.feature.home.components.NowReadingSection

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column (
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.windowInsetsPadding(WindowInsets.statusBars))

        HomeHeader()

        Spacer(Modifier.height(16.dp))

        NowReadingSection()

        Spacer(Modifier.windowInsetsPadding(WindowInsets.navigationBars))
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}