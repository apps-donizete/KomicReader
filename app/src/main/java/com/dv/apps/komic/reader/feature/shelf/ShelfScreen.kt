package com.dv.apps.komic.reader.feature.shelf

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import com.dv.apps.komic.reader.domain.filesystem.VirtualFile
import com.dv.apps.komic.reader.domain.model.Settings
import com.dv.apps.komic.reader.feature.shelf.components.ShelfFilter
import com.dv.apps.komic.reader.feature.shelf.components.ShelfHeader
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
        GridCells.Fixed(columns),
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item { Spacer(Modifier.windowInsetsPadding(WindowInsets.statusBars)) }

        item(span = { span }) {
            ShelfHeader()
        }

        item(span = { span }) {
            ShelfFilter()
        }

        ShelfPreviewTree(span, state.tree)

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
                file = virtualFile
            )
        }

        is VirtualFile.File -> item {
            Thumbnail(
                file = virtualFile
            )
        }

        is VirtualFile.Folder -> {
            item(span = { span }) {
                Spacer(Modifier.height(16.dp))
            }

            item(span = { span }) {
                Text(
                    text = virtualFile.name.uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            for (child in virtualFile.children) {
                ShelfPreviewTree(span, child)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ShelfScreenPreview() {
    KomicReaderTheme {
        ShelfScreen(
            State(
                tree = VirtualFile.Folder(
                    children = listOf(
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
        )
    }
}