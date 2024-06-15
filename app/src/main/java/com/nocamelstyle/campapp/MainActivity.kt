package com.nocamelstyle.campapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nocamelstyle.campapp.screens.golden_poems.GoldenPoemsScreen
import com.nocamelstyle.campapp.screens.leaderboard.LeaderboardScreen
import com.nocamelstyle.campapp.screens.rules.RulesScreen
import com.nocamelstyle.campapp.screens.schedule.ScheduleScreen
import com.nocamelstyle.campapp.screens.songs.list.SongsListScreen
import com.nocamelstyle.campapp.ui.theme.CampAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CampAppTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomBar(navController) }
                ) { innerPadding ->
                    innerPadding
                    BottomBarNavGraph(navController = navController)
                }
            }
        }
    }

    @Composable
    fun BottomBarNavGraph(navController: NavHostController) {

        NavHost(
            navController = navController,
            startDestination = BottomBarScreen.Schedule.route
        ) {
            composable(route = BottomBarScreen.Songs.route) {
                SongsListScreen()
            }
            composable(route = BottomBarScreen.Schedule.route) {
                ScheduleScreen()
            }
            composable(route = BottomBarScreen.Rules.route) {
                RulesScreen {
//                    navController.navigate("rule")
                }
            }
            composable(route = BottomBarScreen.GoldenPoems.route) {
                GoldenPoemsScreen()
            }
            composable(route = BottomBarScreen.Leaderboard.route) {
                LeaderboardScreen()
            }
        }
    }

    @Composable
    fun BottomBar(navController: NavHostController) {
        var screens = listOf(
            BottomBarScreen.Schedule,
            BottomBarScreen.Songs,
            BottomBarScreen.Leaderboard,
            BottomBarScreen.Rules,
            BottomBarScreen.GoldenPoems
        )

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        var currentDestination = navBackStackEntry?.destination

        NavigationBar {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    navDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }


    @Composable
    fun RowScope.AddItem(
        screen: BottomBarScreen,
        navDestination: NavDestination?,
        navController: NavHostController
    ) {
        NavigationBarItem(
            icon = { Icon(imageVector = screen.icon, contentDescription = " NavBar Icon") },
            label = {
                Text(text = screen.title)
            },
            selected = navDestination?.hierarchy?.any { it.route == screen.route } == true,
            onClick = {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            })
    }
}

sealed class BottomBarScreen(
    var route: String,
    var icon: ImageVector,
    var title: String
) {
    object Songs : BottomBarScreen(
        route = "songs",
        icon = Icons.Default.Home,
        title = "Песни"
    )

    object Schedule : BottomBarScreen(
        route = "schedule",
        icon = Icons.Default.Settings,
        title = "Расписание"
    )

    object Leaderboard : BottomBarScreen(
        route = "leaderboard",
        icon = Icons.Default.Person,
        title = "Соревнование"
    )

    object Rules : BottomBarScreen(
        route = "rules",
        icon = Icons.Default.Person,
        title = "Правила"
    )

    object GoldenPoems : BottomBarScreen(
        route = "golden_poems",
        icon = Icons.Default.Person,
        title = "Золотые стихи"
    )
}