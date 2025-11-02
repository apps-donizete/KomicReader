package com.dv.apps.komic.reader.feature.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.rememberNavigationSuiteScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.dv.apps.komic.reader.R
import com.dv.apps.komic.reader.feature.settings.SettingsScreen
import com.dv.apps.komic.reader.ui.theme.KomicReaderTheme

@Composable
fun MainNavigation() {
    val navigationStack = remember { mutableStateListOf(Destination.HOME) }
    val navigationSuiteState = rememberNavigationSuiteScaffoldState()

    NavigationSuiteScaffold(
        state = navigationSuiteState,
        navigationSuiteItems = {
            Destination.entries.forEach { destination ->
                item(
                    selected = navigationStack.single() == destination,
                    onClick = { navigationStack.fill(destination) },
                    icon = {
                        Icon(
                            painterResource(
                                if (navigationStack.single() == destination) {
                                    destination.selectedIcon
                                } else {
                                    destination.icon
                                }
                            ),
                            contentDescription = stringResource(destination.title)
                        )
                    },
                    label = { Text(destination.name) }
                )
            }
        },
    ) {
        if (LocalInspectionMode.current) {
            Text(
                "Hello World", Modifier.windowInsetsTopHeight(
                    WindowInsets.systemBars
                )
            )
        } else {
            MainNavigator(navigationStack)
        }
    }
}

@Composable
fun MainNavigator(
    navigationStack: MutableList<out Any>
) {
    NavDisplay(
        backStack = navigationStack,
        entryProvider = entryProvider {
            entry<Destination> {
                when (it) {
                    Destination.HOME -> Text("Home")
                    Destination.SHELF -> Text("Shelf")
                    Destination.SETTINGS -> SettingsScreen()
                }
            }
        }
    )
}

enum class Destination(
    val title: Int,
    val icon: Int,
    val selectedIcon: Int
) {
    HOME(R.string.menu_home, R.drawable.ic_home, R.drawable.ic_home_filled),
    SHELF(R.string.menu_shelf, R.drawable.ic_shelf, R.drawable.ic_shelf_filled),
    SETTINGS(R.string.menu_settings, R.drawable.ic_settings, R.drawable.ic_settings_filled)
}

@PreviewScreenSizes
@Composable
private fun FolderScreenPreview() {
    KomicReaderTheme {
        MainNavigation()
    }
}