package com.dv.apps.komic.reader.feature.settings.folder

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import com.dv.apps.komic.reader.domain.model.DocumentTree
import com.dv.apps.komic.reader.ext.dispatchFor
import com.dv.apps.komic.reader.feature.settings.SettingsSection
import com.dv.apps.komic.reader.ui.theme.KomicReaderTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun FolderSourceSettingsSection() {
    if (LocalInspectionMode.current) {
        FolderSourceSettingsSection(State())
    } else {
        val vm = koinViewModel<FolderSourceSettingsSectionViewModel>()
        val state by vm.state.collectAsStateWithLifecycle()
        FolderSourceSettingsSection(state, vm::handleIntent)
    }
}

@Composable
fun FolderSourceSettingsSection(
    state: State,
    dispatchIntent: (Intent) -> Unit = {}
) {
    val openDocumentTreeLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocumentTree(),
        dispatchIntent dispatchFor Intent::OnFileTreeSelected
    )

    Column {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                stringResource(R.string.folder_source_settings_section_title),
                style = MaterialTheme.typography.titleMedium
            )

            IconButton(
                onClick = { openDocumentTreeLauncher.launch(null) }
            ) {
                Icon(
                    painterResource(R.drawable.ic_folder_add),
                    contentDescription = ""
                )
            }
        }

        if (state.selectedFolders.isNotEmpty()) {
            HorizontalDivider(
                Modifier.padding(horizontal = 8.dp)
            )

            Column(Modifier.padding(vertical = 8.dp)) {
                for (folder in state.selectedFolders) {
                    Text(folder.name)
                }
            }
        }
    }
}

@Preview
@Composable
private fun FolderScreenPreview1() {
    KomicReaderTheme {
        SettingsSection {
            FolderSourceSettingsSection(
                state = State(
                    selectedFolders = List(4) {
                        DocumentTree(
                            "/root/sdcard:folder/$it",
                            "folder/$it"
                        )
                    }
                )
            )
        }
    }
}

@Preview
@Composable
private fun FolderScreenPreview2() {
    KomicReaderTheme {
        SettingsSection {
            FolderSourceSettingsSection(
                state = State(
                    selectedFolders = emptyList()
                )
            )
        }
    }
}