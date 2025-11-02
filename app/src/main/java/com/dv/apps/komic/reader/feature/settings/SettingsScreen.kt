package com.dv.apps.komic.reader.feature.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dv.apps.komic.reader.feature.settings.folder.FolderSourceSettingsSection
import com.dv.apps.komic.reader.feature.settings.folder.FolderSourceSettingsSectionViewModel
import com.dv.apps.komic.reader.ui.theme.KomicReaderTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen() {
    Column(
        Modifier
            .verticalScroll(
                rememberScrollState()
            )
            .windowInsetsPadding(
                WindowInsets.statusBars
            )
    ) {
        SettingsSection {
            val vm = koinViewModel<FolderSourceSettingsSectionViewModel>()
            val state by vm.state.collectAsStateWithLifecycle()
            FolderSourceSettingsSection(state, vm::handleIntent)
        }
    }
}

@Composable
fun SettingsSection(
    body: @Composable () -> Unit
) {
    Card(
        Modifier
            .padding(8.dp)
            .fillMaxWidth(),
    ) {
        Box(Modifier.padding(horizontal = 16.dp)) {
            body()
        }
    }
}

@Preview
@Composable
private fun SettingsSectionPreview() {
    KomicReaderTheme {
        SettingsSection {
            Text("Hello World")
        }
    }
}

@PreviewScreenSizes
@Composable
fun SettingsScreenPreview() {
    KomicReaderTheme {
        SettingsScreen()
    }
}

