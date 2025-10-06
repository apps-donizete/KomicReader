package com.dv.apps.komic.reader

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.rememberNavigationSuiteScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.documentfile.provider.DocumentFile
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.dv.apps.komic.reader.ui.theme.KomicReaderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KomicReaderTheme {
                MainScreen()
            }
        }
    }
}

enum class Destination {
    SONGS,
    FOLDER
}

@Composable
fun MainScreen() {
    val navigationStack = remember { mutableStateListOf(Destination.SONGS) }
    val navigationSuiteState = rememberNavigationSuiteScaffoldState()

    NavigationSuiteScaffold(
        state = navigationSuiteState,
        navigationSuiteItems = {
            Destination.entries.forEachIndexed { index, destination ->
                item(
                    selected = navigationStack.first().ordinal == index,
                    onClick = {
                        navigationStack.clear()
                        navigationStack.add(destination)
                    },
                    icon = {
                        Icon(
                            painterResource(R.drawable.ic_android),
                            contentDescription = null
                        )
                    },
                    label = { Text(destination.name) }
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.secondaryContainer
    ) {
        NavDisplay(
            backStack = navigationStack,
            entryProvider = entryProvider {
                entry<Destination> {
                    when (it) {
                        Destination.SONGS -> Text("Songs screen")
                        Destination.FOLDER -> FolderManagementScreen()
                    }
                }
            }
        )
    }
}

@Composable
fun FolderManagementScreen() {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocumentTree()
    ) { uri ->
        if (uri != null) {
            context.contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            val tree = DocumentFile.fromTreeUri(context, uri)
            val files = tree?.listFiles()
            val names = files?.map {
                it.name
            }
            println(names)
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                launcher.launch(null)
            }) {
                Icon(
                    painterResource(R.drawable.ic_android),
                    contentDescription = null
                )
            }
        }
    ) {
        Text("Open documents tree", modifier = Modifier.padding(it))
    }
}