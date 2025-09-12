package siarhei.luskanau.pixabayeye.ui.debug

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import org.koin.core.Koin

@OptIn(ExperimentalMaterial3Api::class)
fun EntryProviderScope<NavKey>.debugGraph(koin: Koin) {
    entry<DebugGraph> {
        val tabs = listOf(
            NavigationBarItemData(DatastoreRoute, "Datastore", Icons.Filled.Settings),
            NavigationBarItemData(NetworkRoute, "Network", Icons.Filled.Public)
        )
        var navigationBarItemData by remember { mutableStateOf(tabs.first()) }
        val backStack = mutableStateListOf<NavKey>(DatastoreRoute)
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = navigationBarItemData.label) })
            },
            bottomBar = {
                NavigationBar {
                    tabs.forEach { item ->
                        NavigationBarItem(
                            selected = navigationBarItemData.route == item.route,
                            onClick = {
                                navigationBarItemData = item
                                backStack.add(item.route)
                            },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(text = item.label) }
                        )
                    }
                }
            }
        ) { innerPadding ->
            DebugNavDisplay(koin = koin, backStack = backStack, innerPadding = innerPadding)
        }
    }
}

data class NavigationBarItemData(val route: DebugRoutes, val label: String, val icon: ImageVector)

@Serializable data object DebugGraph : NavKey
