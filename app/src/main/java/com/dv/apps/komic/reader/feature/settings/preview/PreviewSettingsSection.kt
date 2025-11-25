package com.dv.apps.komic.reader.feature.settings.preview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dv.apps.komic.reader.R
import com.dv.apps.komic.reader.domain.model.Settings
import com.dv.apps.komic.reader.feature.settings.SettingsSection
import com.dv.apps.komic.reader.ui.theme.KomicReaderTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun PreviewSettingsSection() {
    if (LocalInspectionMode.current) {
        PreviewSettingsSection(State())
    } else {
        val vm = koinViewModel<PreviewSettingsSectionViewModel>()
        val state by vm.state.collectAsStateWithLifecycle()
        PreviewSettingsSection(state, vm::handleIntent)
    }
}

@Composable
fun PreviewSettingsSection(
    state: State,
    dispatchIntent: (Intent) -> Unit = {}
) {
    Column {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                stringResource(R.string.settings_section_preview_options_title),
                style = MaterialTheme.typography.titleMedium
            )

            IconButton(
                onClick = { }
            ) {
                Icon(
                    painterResource(R.drawable.ic_preview),
                    contentDescription = ""
                )
            }
        }

        HorizontalDivider(Modifier.padding(horizontal = 8.dp))

        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                stringResource(R.string.settings_section_preview_options_vertical_span)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    dispatchIntent(
                        Intent.OnVerticalPreviewSpanSizeChanged(
                            state.verticalPreviewSpanSize - 1
                        )
                    )
                }) {
                    Icon(
                        painterResource(R.drawable.ic_arrow_left),
                        contentDescription = ""
                    )
                }
                Text("${state.verticalPreviewSpanSize}")
                IconButton(onClick = {
                    dispatchIntent(
                        Intent.OnVerticalPreviewSpanSizeChanged(
                            state.verticalPreviewSpanSize + 1
                        )
                    )
                }) {
                    Icon(
                        painterResource(R.drawable.ic_arrow_right),
                        contentDescription = ""
                    )
                }
            }
        }

        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                stringResource(R.string.settings_section_preview_options_horizontal_span)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    dispatchIntent(
                        Intent.OnHorizontalPreviewSpanSizeChanged(
                            state.horizontalPreviewSpanSize - 1
                        )
                    )
                }) {
                    Icon(
                        painterResource(R.drawable.ic_arrow_left),
                        contentDescription = ""
                    )
                }
                Text("${state.horizontalPreviewSpanSize}")
                IconButton(onClick = {
                    dispatchIntent(
                        Intent.OnHorizontalPreviewSpanSizeChanged(
                            state.horizontalPreviewSpanSize + 1
                        )
                    )
                }) {
                    Icon(
                        painterResource(R.drawable.ic_arrow_right),
                        contentDescription = ""
                    )
                }
            }
        }

        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                stringResource(R.string.settings_section_preview_quality)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                data class QualityView(
                    val quality: Settings.Quality,
                    val icon: Int,
                    val iconFilled: Int
                )
                listOf(
                    QualityView(
                        Settings.Quality.HD,
                        R.drawable.ic_quality_hd,
                        R.drawable.ic_quality_hd_filled
                    ),
                    QualityView(
                        Settings.Quality.FULL_HD,
                        R.drawable.ic_quality_full_hd,
                        R.drawable.ic_quality_full_hd_filled
                    ),
                    QualityView(
                        Settings.Quality.TWO_K,
                        R.drawable.ic_quality_2k,
                        R.drawable.ic_quality_2k_filled
                    ),
                    QualityView(
                        Settings.Quality.FOUR_K,
                        R.drawable.ic_quality_4k,
                        R.drawable.ic_quality_4k_filled
                    )
                ).forEach {
                    IconButton(onClick = {
                        if (it.quality == state.quality) return@IconButton
                        dispatchIntent(Intent.OnQualityChanged(it.quality))
                    }) {
                        Icon(
                            painterResource(
                                if (it.quality == state.quality) {
                                    it.iconFilled
                                } else {
                                    it.icon
                                }
                            ),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSettingsSectionPreview() {
    KomicReaderTheme {
        SettingsSection {
            PreviewSettingsSection()
        }
    }
}

