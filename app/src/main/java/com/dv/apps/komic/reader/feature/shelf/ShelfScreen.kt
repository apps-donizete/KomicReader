package com.dv.apps.komic.reader.feature.shelf

import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import com.dv.apps.komic.reader.R
import com.dv.apps.komic.reader.domain.model.KomicPreviewTree
import com.dv.apps.komic.reader.feature.common.KomicPreview
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
    val configuration = LocalConfiguration.current
    val isVertical = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val spanSize = if (isVertical) {
        state.verticalPreviewColumnSize
    } else {
        state.horizontalPreviewColumnSize
    }.takeIf { it > 0 } ?: run {
        val windowInfo = currentWindowAdaptiveInfo(supportLargeAndXLargeWidth = true)
        val windowSizeClass = windowInfo.windowSizeClass
        val horizontallyLarge = windowSizeClass.isWidthAtLeastBreakpoint(
            WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND
        )
        if (horizontallyLarge) {
            4
        } else {
            2
        }
    }

    LazyVerticalGrid(
        GridCells.Fixed(spanSize)
    ) {
        item { Spacer(Modifier.windowInsetsPadding(WindowInsets.statusBars)) }

        item(
            span = { GridItemSpan(spanSize) }
        ) {
            Text(
                stringResource(R.string.shelf_screen_title),
                style = MaterialTheme.typography.titleLarge
            )
        }

        for (item in state.komicPreviewTrees) {
            ShelfPreviewTree(spanSize, item)
        }

        item { Spacer(Modifier.windowInsetsPadding(WindowInsets.navigationBars)) }
    }
}

fun LazyGridScope.ShelfPreviewTree(
    spanSize: Int,
    komicPreviewTree: KomicPreviewTree
) {
    when (komicPreviewTree) {
        KomicPreviewTree.Empty -> item {
            Text("Something wrong happened")
        }

        is KomicPreviewTree.Done -> item {
            KomicPreview(
                modifier = Modifier.padding(8.dp),
                title = komicPreviewTree.title,
                preview = komicPreviewTree.preview
            )
        }

        is KomicPreviewTree.Nested -> {
            item(
                span = { GridItemSpan(spanSize) }
            ) {
                Spacer(Modifier.height(32.dp))
            }
            item(
                span = { GridItemSpan(spanSize) }
            ) {
                Text(
                    komicPreviewTree.title,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            for (child in komicPreviewTree.children) {
                ShelfPreviewTree(spanSize, child)
            }
        }
    }
}

@PreviewScreenSizes
@Composable
private fun ShelfScreenPreview() {
    KomicReaderTheme {
        ShelfScreen(
            State(
                komicPreviewTrees = listOf(
                    KomicPreviewTree.Nested(
                        "POKEMON",
                        listOf(
                            KomicPreviewTree.Done(
                                "A"
                            ),
                            KomicPreviewTree.Done(
                                "B"
                            )
                        )
                    ),
                    KomicPreviewTree.Nested(
                        "DIGIMON",
                        listOf(
                            KomicPreviewTree.Done(
                                "A"
                            ),
                            KomicPreviewTree.Done(
                                "B"
                            )
                        )
                    ),
                    KomicPreviewTree.Nested(
                        "CROSSOVER",
                        listOf(
                            KomicPreviewTree.Done(
                                "A"
                            ),
                            KomicPreviewTree.Nested(
                                "DIGIMON",
                                listOf(
                                    KomicPreviewTree.Done(
                                        "A"
                                    ),
                                    KomicPreviewTree.Done(
                                        "B"
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
    }
}