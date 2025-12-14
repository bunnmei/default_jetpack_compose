package space.webkombinat.defaultjetpackcompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.google.firebase.auth.FirebaseAuth
import space.webkombinat.compass.CompassScreen
import space.webkombinat.defaultjetpackcompose.R
import space.webkombinat.defaultjetpackcompose.components.BottomNavBar
import space.webkombinat.defaultjetpackcompose.data.BottomNavigationItems
import space.webkombinat.login.LoginNavItems
import space.webkombinat.login.LoginScreen
import space.webkombinat.login.UserDataScreen

@Composable
fun MainAppScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val BottonNavigationExpanded by rememberSaveable { mutableStateOf(true) }
    val bottomNavigationItems = listOf(
        BottomNavigationItems.Compass,
        BottomNavigationItems.Login,
        BottomNavigationItems.Mapbox,
        BottomNavigationItems.Level,
    )
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route
    val currentSelect = rememberSaveable { mutableStateOf(0) }

    LaunchedEffect(key1 = currentRoute) {
        if (currentRoute == null) return@LaunchedEffect

        bottomNavigationItems.forEachIndexed { index, items ->
            if (currentRoute == items.route) {
                currentSelect.value = index
            } else if (currentRoute in LoginNavItems.loginRoutes) {
                currentSelect.value = 1
            }
        }
        println("route - ${currentSelect.value}")
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                expand = { bool -> },
                select = currentSelect,
                items = bottomNavigationItems,
                onItemClick = {
                    if (bottomNavigationItems.contains(it)) {

                        navController.navigate(it.route){
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        println("screen--${currentRoute}")
        Column(modifier = modifier.padding(paddingValues)) {
            NavHost(
                navController = navController,
                startDestination = BottomNavigationItems.Compass.route
            ) {
                composable(BottomNavigationItems.Compass.route) {
                    CompassScreen()
                }

                loginScreens(navCont = navController)

                composable(route = BottomNavigationItems.Mapbox.route) {
                    Column(
                        modifier = modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("MapBox")
                    }
                }

                composable(route = BottomNavigationItems.Level.route) {
                    Column(
                        modifier = modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Level")
                    }
                }
            }
        }
    }

}

fun NavGraphBuilder.loginScreens(
    navCont: NavHostController,
){

    var startDestination = LoginNavItems.Login.route
    val auth = FirebaseAuth.getInstance()
    if (auth.currentUser != null) {
        startDestination = LoginNavItems.UserData.route
    }

    navigation(
        startDestination = startDestination,
        route = BottomNavigationItems.Login.route
    ) {
        composable(
            LoginNavItems.Login.route
        ) {
            LoginScreen(
                web_google_key = stringResource( R.string.default_web_client_id),
                nav = {
                    navCont.navigate(LoginNavItems.UserData.route)
                }
            )
        }

        composable(
            LoginNavItems.UserData.route
        ) {
            UserDataScreen(
                logout = {
                    navCont.navigate(LoginNavItems.Login.route)
                }
            )
        }
    }
}