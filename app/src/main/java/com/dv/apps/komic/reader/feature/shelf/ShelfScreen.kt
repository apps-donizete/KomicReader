package com.dv.apps.komic.reader.feature.shelf

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dv.apps.komic.reader.R
import com.dv.apps.komic.reader.domain.model.FileTree
import com.dv.apps.komic.reader.ui.theme.KomicReaderTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ShelfScreen() {
    if (LocalInspectionMode.current) {
        ShelfScreen(State())
    } else {
        val vm = koinViewModel<ShelfScreenViewModel>()
        val state by vm.state.collectAsStateWithLifecycle()
        ShelfScreen(state, vm::handleIntent)
    }
}

@Composable
fun ShelfScreen(
    state: State,
    dispatchIntent: (Intent) -> Unit = {}
) {
    Column(
        Modifier.windowInsetsPadding(WindowInsets.statusBars)
    ) {
        Text(
            stringResource(R.string.shelf_screen_title),
            style = MaterialTheme.typography.titleLarge
        )

        LazyColumn(
            Modifier.fillMaxSize()
        ) {
            items(state.fileTrees) {
                ShelfFileTree(it)
            }
        }
    }
}

@Composable
fun ShelfFileTree(fileTree: FileTree) {
    when (fileTree) {
        FileTree.Empty -> Text("Something wrong happened")
        is FileTree.File -> Text("File: ${fileTree.name}")
        is FileTree.Folder -> {
            Column {
                Text("Folder: ${fileTree.name}")

                for (child in fileTree.children) {
                    ShelfFileTree(child)
                }
            }
        }
    }
}

@PreviewScreenSizes
@Composable
private fun ShelfScreenPreview() {
    KomicReaderTheme {
        ShelfScreen()
    }
}