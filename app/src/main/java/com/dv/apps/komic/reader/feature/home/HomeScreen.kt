package com.dv.apps.komic.reader.feature.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dv.apps.komic.reader.ui.theme.KomicReaderTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Text("Hello World")
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    KomicReaderTheme {
        HomeScreen()
    }
}