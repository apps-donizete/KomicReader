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
import com.dv.apps.komic.reader.domain.filesystem.VirtualFile
import com.dv.apps.komic.reader.domain.model.Settings
import com.dv.apps.komic.reader.ui.theme.KomicReaderTheme
import com.dv.apps.komic.reader.ui.thumbnail.Thumbnail
import org.koin.androidx.compose.koinViewModel

@Composable
fun ShelfScreen() {
    if (LocalInspectionMode.current) {
        ShelfScreen(
            State(
                settings = Settings(
                    verticalPreviewSpanSize = 3,
                    horizontalPreviewSpanSize = 3
                )
            )
        )
    } else {
        val vm = koinViewModel<ShelfScreenViewModel>()
        val state by vm.state.collectAsStateWithLifecycle()
        ShelfScreen(state, vm::handleIntent)
    }
}

@Composable
fun getSettingsSpan(state: State): Int? {
    val configuration = LocalConfiguration.current
    val isVertical = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    return if (isVertical) {
        state.settings.verticalPreviewSpanSize
    } else {
        state.settings.horizontalPreviewSpanSize
    }.takeIf { it > 0 }
}

@Composable
fun getDefaultSpan(): Int {
    val windowInfo = currentWindowAdaptiveInfo(supportLargeAndXLargeWidth = true)
    val windowSizeClass = windowInfo.windowSizeClass
    val horizontallyLarge = windowSizeClass.isWidthAtLeastBreakpoint(
        WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND
    )
    return if (horizontallyLarge) {
        4
    } else {
        2
    }
}

@Composable
fun ShelfScreen(
    state: State,
    dispatchIntent: (Intent) -> Unit = {}
) {
    val columns = getSettingsSpan(state) ?: getDefaultSpan()
    val span = GridItemSpan(columns)

    LazyVerticalGrid(
        GridCells.Fixed(columns)
    ) {
        item { Spacer(Modifier.windowInsetsPadding(WindowInsets.statusBars)) }

        item(span = { span }) {
            Text(
                stringResource(R.string.shelf_screen_title),
                style = MaterialTheme.typography.titleLarge
            )
        }

        for (item in state.trees) {
            ShelfPreviewTree(span, item)
        }

        item { Spacer(Modifier.windowInsetsPadding(WindowInsets.navigationBars)) }
    }
}

fun LazyGridScope.ShelfPreviewTree(
    span: GridItemSpan,
    virtualFile: VirtualFile
) {
    when (virtualFile) {
        is VirtualFile.File.WithThumbnail -> item {
            Thumbnail(
                modifier = Modifier.padding(8.dp),
                file = virtualFile
            )
        }

        is VirtualFile.File -> item {
            Thumbnail(
                modifier = Modifier.padding(8.dp),
                file = virtualFile
            )
        }

        is VirtualFile.Folder -> {
            item(span = { span }) {
                Spacer(Modifier.height(32.dp))
            }

            item(span = { span }) {
                Text(
                    virtualFile.name,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            for (child in virtualFile.children) {
                ShelfPreviewTree(span, child)
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
                trees = listOf(
                    VirtualFile.Folder(
                        "POKEMON",
                        "",
                        listOf(
                            VirtualFile.File(
                                "A"
                            ),
                            VirtualFile.File(
                                "B"
                            ),
                            VirtualFile.File(
                                "C"
                            ),
                        )
                    ),
                    VirtualFile.Folder(
                        "DIGIMON",
                        "",
                        listOf(
                            VirtualFile.File(
                                "A"
                            ),
                            VirtualFile.File(
                                "B"
                            ),
                        )
                    ),
                    VirtualFile.Folder(
                        "CROSSOVER",
                        "",
                        listOf(
                            VirtualFile.File(
                                "A"
                            ),
                            VirtualFile.File(
                                "B",
                            ),
                            VirtualFile.Folder(
                                "CHAVEZ",
                                "",
                                listOf(
                                    VirtualFile.File(
                                        "A"
                                    ),
                                    VirtualFile.File(
                                        "B"
                                    ),
                                )
                            )
                        )
                    )
                )
            )
        )
    }
}