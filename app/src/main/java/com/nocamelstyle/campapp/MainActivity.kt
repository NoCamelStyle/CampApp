package com.nocamelstyle.campapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
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
import com.nocamelstyle.campapp.screens.ask.AskScreen
import com.nocamelstyle.campapp.screens.camp_info.CampInfoScreen
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

        val startDestination = when (intent.data?.toString()?.split("/")?.last()) {
            "songs" -> "songs"
            "calendar" -> "schedule"
            "poems" -> "golden_poems"
            else -> "schedule"
        }

        enableEdgeToEdge()
        setContent {
            CampAppTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { TopBar(navController) },
                    bottomBar = { BottomBar(navController) }
                ) { innerPadding ->
                    BottomBarNavGraph(startDestination, innerPadding, navController)
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun TopBar(navController: NavHostController) {
        val context = LocalContext.current
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text("Лагерь 'Солнышко'")
            },
            actions = {
                IconButton(onClick = {
                    context.sendAsk()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = "Задать вопрос"
                    )
                }
                IconButton(onClick = { navController.navigate("about") }) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "О лагере"
                    )
                }
            },
            navigationIcon = {
                //todo
//                if (navController.currentBackStack.value.size > 1)
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Назад"
                    )
                }
            }
        )
    }

    @Composable
    fun BottomBarNavGraph(
        startDestination: String,
        paddingValues: PaddingValues,
        navController: NavHostController
    ) {

        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues)
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
            composable("ask") {
                AskScreen()
            }
            composable("about") {
                CampInfoScreen()
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

private fun Context.sendAsk() {
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.setData(Uri.parse("mailto:andreysxkormachenko@gmail.com")) // only email apps should handle this
    intent.putExtra(Intent.EXTRA_SUBJECT, "Вопрос")
    intent.putExtra(Intent.EXTRA_TEXT, "Хочу узнать")
    runCatching { startActivity(intent) }
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
        title = "График"
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
        title = "Стихи"
    )
}