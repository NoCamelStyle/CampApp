package com.nocamelstyle.campapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nocamelstyle.campapp.screens.golden_poems.GoldenPoemsScreen
import com.nocamelstyle.campapp.screens.leaderboard.LeaderboardScreen
import com.nocamelstyle.campapp.screens.rules.RulesScreen
import com.nocamelstyle.campapp.screens.rules.allRules
import com.nocamelstyle.campapp.screens.rules.rule.RuleScreen
import com.nocamelstyle.campapp.screens.schedule.ScheduleScreen
import com.nocamelstyle.campapp.screens.songs.list.SongsListScreen
import com.nocamelstyle.campapp.screens.songs.song.SongScreen
import com.nocamelstyle.campapp.screens.songs.songs
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
                    topBar = { TopBar(navController) },
                    bottomBar = { BottomBar(navController) }
                ) { innerPadding ->
                    BottomBarNavGraph(innerPadding, navController)
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun TopBar(navController: NavHostController) {
        TopAppBar (
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text("")
            },
            actions = {
                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = "Задать вопрос"
                    )
                }
                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        imageVector = Icons.Filled.Infok,
                        contentDescription = "О лагере"
                    )
                }
            }
        )
    }

    @Composable
    fun BottomBarNavGraph(
        paddingValues: PaddingValues,
        navController: NavHostController
    ) {

        NavHost(
            navController = navController,
            startDestination = BottomBarScreen.Schedule.route,
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            composable(route = BottomBarScreen.Songs.route) {
                SongsListScreen {
                    navController.navigate("song/${it.id}")
                }
            }
            composable(route = BottomBarScreen.Schedule.route) {
                ScheduleScreen()
            }
            composable(route = BottomBarScreen.Rules.route) {
                RulesScreen {
                    navController.navigate("rule/${it.id}")
                }
            }
            composable(route = BottomBarScreen.GoldenPoems.route) {
                GoldenPoemsScreen()
            }
            composable(route = BottomBarScreen.Leaderboard.route) {
                LeaderboardScreen()
            }
            composable(
                "rule/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) {
                RuleScreen(allRules.first { rule -> rule.id == it.arguments?.getInt("id") })
            }
            composable(
                "song/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) {
                SongScreen(songs.first { song -> song.id == it.arguments?.getInt("id") })
            }
        }
    }

    @Composable
    fun BottomBar(navController: NavHostController) {
        val screens = listOf(
            BottomBarScreen.Schedule,
            BottomBarScreen.Songs,
            BottomBarScreen.Leaderboard,
            BottomBarScreen.Rules,
            BottomBarScreen.GoldenPoems
        )

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

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
                Text(
                    text = screen.title,
                    textAlign = TextAlign.Center
                )
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
        icon = Icons.Default.List,
        title = "Песни"
    )

    object Schedule : BottomBarScreen(
        route = "schedule",
        icon = Icons.Default.DateRange,
        title = "Расписание"
    )

    object Leaderboard : BottomBarScreen(
        route = "leaderboard",
        icon = Icons.Default.ThumbUp,
        title = "Рейтинг"
    )

    object Rules : BottomBarScreen(
        route = "rules",
        icon = Icons.Default.Person,
        title = "Правила"
    )

    object GoldenPoems : BottomBarScreen(
        route = "golden_poems",
        icon = Icons.Default.Star,
        title = "Золотые стихи"
    )
}