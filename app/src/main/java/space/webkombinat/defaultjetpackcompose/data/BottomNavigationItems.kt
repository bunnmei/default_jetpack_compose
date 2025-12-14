package space.webkombinat.defaultjetpackcompose.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.VerticalAlignCenter
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationItems(
    val route: String,
    val icon: ImageVector,
    val label: String,
) {
    object Compass : BottomNavigationItems(route = "compass", icon = Icons.Default.Explore, label = "compass")
    object Login: BottomNavigationItems(route = "login", icon = Icons.AutoMirrored.Filled.Login, label = "login")
    object Mapbox: BottomNavigationItems(route = "mapbox", icon = Icons.Default.Map, label = "mapbox")
    object Level: BottomNavigationItems(route = "level", icon = Icons.Default.VerticalAlignCenter, label = "level")
//    object Level: BottomNavigationItems(route = "level", icon = Icons.Default.VerticalAlignCenter, label = "level")
}