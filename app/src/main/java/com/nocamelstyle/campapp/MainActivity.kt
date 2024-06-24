package com.nocamelstyle.campapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.collectAsState
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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.nocamelstyle.campapp.screens.ask.AskScreen
import com.nocamelstyle.campapp.screens.camp_info.CampInfoScreen
import com.nocamelstyle.campapp.screens.golden_poems.GoldenPoem
import com.nocamelstyle.campapp.screens.golden_poems.GoldenPoemsScreen
import com.nocamelstyle.campapp.screens.golden_poems.goldenPoems
import com.nocamelstyle.campapp.screens.leaderboard.LeaderRow
import com.nocamelstyle.campapp.screens.leaderboard.LeaderboardScreen
import com.nocamelstyle.campapp.screens.leaderboard.leaderboardTable
import com.nocamelstyle.campapp.screens.rules.Rule
import com.nocamelstyle.campapp.screens.rules.RulesScreen
import com.nocamelstyle.campapp.screens.rules.allRules
import com.nocamelstyle.campapp.screens.rules.rule.RuleScreen
import com.nocamelstyle.campapp.screens.schedule.ScheduleItem
import com.nocamelstyle.campapp.screens.schedule.ScheduleScreen
import com.nocamelstyle.campapp.screens.schedule.createDate
import com.nocamelstyle.campapp.screens.schedule.scheduleList
import com.nocamelstyle.campapp.screens.songs.Song
import com.nocamelstyle.campapp.screens.songs.list.SongsListScreen
import com.nocamelstyle.campapp.screens.songs.song.SongScreen
import com.nocamelstyle.campapp.screens.songs.songsDefault
import com.nocamelstyle.campapp.ui.theme.CampAppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class MainActivity : ComponentActivity() {

    private val db by lazy { Firebase.firestore }

    private val verses = MutableStateFlow(goldenPoems)
    private val songs = MutableStateFlow(songsDefault)
    private val rules = MutableStateFlow(allRules)
    private val leaderboard = MutableStateFlow(leaderboardTable)
    private val schedule = MutableStateFlow(scheduleList)

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

        db.collection("verses")
            .get()
            .addOnSuccessListener { result ->
                verses.update {
                    result.map {
                        GoldenPoem(
                            title = "День ${it["day"] as Long}",
                            poem = it["value"] as String,
                            link = it["link"] as String
                        )
                    }
                }
            }
            .addOnFailureListener {
                Log.e("verses", "addOnFailureListener", it)
            }

        db.collection("songs")
            .get()
            .addOnSuccessListener { result ->
                songs.update {
                    result.map {
                        Song(
                            title = it["title"] as String,
                            id = System.currentTimeMillis().toInt(),
                            song = it["song"] as String
                        )
                    }
                }
            }

        db.collection("rules")
            .get()
            .addOnSuccessListener { result ->
                rules.update {
                    result.map {
                        Rule(
                            title = it["title"] as String,
                            id = System.currentTimeMillis().toInt(),
                            description = it["description"] as String,
                            rule = it["rule"] as String
                        )
                    }
                }
            }

        db.collection("leaderboard")
            .get()
            .addOnSuccessListener { result ->
                leaderboard.update {
                    result.map {
                        LeaderRow(
                            teamName = it["teamName"] as String,
                            daysPoints = (it["daysPoints"] as List<Long>).map { it.toInt() }
                        )
                    }
                }
            }

        db.collection("schedule")
            .get()
            .addOnSuccessListener { result ->
                schedule.update {
                    result.map {
                        ScheduleItem(
                            title = it["title"] as String,
                            timeStart = (it["startTime"] as com.google.firebase.Timestamp).toDate()
                                .let {
                                    createDate(
                                        hoursValue = it.hours,
                                        minutesValue = it.minutes
                                    )
                                },
                            timeEnd = (it["endTime"] as com.google.firebase.Timestamp).toDate()
                                .let {
                                    createDate(
                                        hoursValue = it.hours,
                                        minutesValue = it.minutes
                                    )
                                }
                        )
                    }
                }
            }

        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        Firebase.remoteConfig.setConfigSettingsAsync(configSettings)
        Firebase.remoteConfig.fetchAndActivate()
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
                    context.sendAsk(
                        Firebase.remoteConfig.getString("email")
                            .takeIf { it.isNotEmpty() }
                            ?: "andreysxkormachenko@gmail.com"
                    )
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

        val poemsList by verses.collectAsState()
        val songsList by songs.collectAsState()
        val rulesList by rules.collectAsState()
        val schedulesList by schedule.collectAsState()
        val leaderBoardList by leaderboard.collectAsState()

        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = BottomBarScreen.Songs.route) {
                SongsListScreen(songsList) {
                    navController.navigate("song/${it.id}")
                }
            }
            composable(route = BottomBarScreen.Schedule.route) {
                ScheduleScreen(schedulesList)
            }
            composable(route = BottomBarScreen.Rules.route) {
                RulesScreen(rulesList) {
                    navController.navigate("rule/${it.id}")
                }
            }
            composable(route = BottomBarScreen.GoldenPoems.route) {
                GoldenPoemsScreen(poemsList)
            }
            composable(route = BottomBarScreen.Leaderboard.route) {
                LeaderboardScreen(leaderBoardList)
            }
            composable(
                "rule/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) {
                RuleScreen(rulesList.first { rule -> rule.id == it.arguments?.getInt("id") })
            }
            composable(
                "song/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) {
                SongScreen(songsList.first { song -> song.id == it.arguments?.getInt("id") })
            }
            composable("ask") {
                AskScreen()
            }
            composable("about") {
                CampInfoScreen(
                    phone = Firebase.remoteConfig.getString("contact_phone")
                        .takeIf { it.isNotEmpty() }
                        ?: "+79001858010",

                    location = Firebase.remoteConfig.getString("camp_location")
                        .takeIf { it.isNotEmpty() }
                        ?.split(";")
                        ?.map { it.toDouble() }
                        ?.let { Pair(it.first(), it.last()) }
                        ?: Pair(33.7, 42.4),

                    items = Firebase.remoteConfig.getString("things_list")
                        .takeIf { it.isNotEmpty() }
                        ?.split(",")
                        ?.map { it.trim() }
                        ?: listOf(
                            "кепку",
                            "шорты (ниже колен)",
                            "Библию",
                            "постельное",
                            "подушку",
                            "полотенце"
                        ),

                    camps = Firebase.remoteConfig.getString("camps")
                        .takeIf { it.isNotEmpty() }
                        ?.split(";")
                        ?.map { it.trim() }
                        ?: listOf(
                            "1 смена (11-15 лет) с 08.06-18.06",
                            "2 смена (11-15 лет) с 08.06-18.06",
                            "3 смена (11-15 лет) с 08.06-18.06",
                            "4 смена (11-15 лет) с 08.06-18.06"
                        ),

                    locationName = Firebase.remoteConfig.getString("location_name")
                        .takeIf { it.isNotEmpty() }
                        ?: "Лагерь проходит на базе Нива в Благодатном (нажми чтобы открыть)",

                    phoneName = Firebase.remoteConfig.getString("phone_name")
                        .takeIf { it.isNotEmpty() }
                        ?: "Андрей"
                )
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

private fun Context.sendAsk(email: String) {
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.setData(Uri.parse("mailto:$email")) // only email apps should handle this
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