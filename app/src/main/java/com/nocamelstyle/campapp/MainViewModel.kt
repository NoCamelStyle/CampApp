package com.nocamelstyle.campapp

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.nocamelstyle.campapp.screens.golden_poems.GoldenPoem
import com.nocamelstyle.campapp.screens.golden_poems.goldenPoems
import com.nocamelstyle.campapp.screens.leaderboard.LeaderRow
import com.nocamelstyle.campapp.screens.leaderboard.leaderboardTable
import com.nocamelstyle.campapp.screens.rules.Rule
import com.nocamelstyle.campapp.screens.rules.allRules
import com.nocamelstyle.campapp.screens.schedule.ScheduleItem
import com.nocamelstyle.campapp.screens.schedule.createDate
import com.nocamelstyle.campapp.screens.schedule.scheduleList
import com.nocamelstyle.campapp.screens.songs.Song
import com.nocamelstyle.campapp.screens.songs.songsDefault
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel {

    private val db by lazy { Firebase.firestore }

    val verses = MutableStateFlow(goldenPoems)
    val songs = MutableStateFlow(songsDefault)
    val rules = MutableStateFlow(allRules)
    val leaderboard = MutableStateFlow(leaderboardTable)
    val schedule = MutableStateFlow(scheduleList)

    fun update() {
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

    fun getConfigStore(): ConfigStore {
        return ConfigStore(
            email = Firebase.remoteConfig.getString("email")
                .takeIf { it.isNotEmpty() }
                ?: defaultConfigStore.email,

            phone = Firebase.remoteConfig.getString("contact_phone")
                .takeIf { it.isNotEmpty() }
                ?: defaultConfigStore.phone,

            location = Firebase.remoteConfig.getString("camp_location")
                .takeIf { it.isNotEmpty() }
                ?.split(";")
                ?.map { it.toDouble() }
                ?.let { Pair(it.first(), it.last()) }
                ?: defaultConfigStore.location,

            items = Firebase.remoteConfig.getString("things_list")
                .takeIf { it.isNotEmpty() }
                ?.split(",")
                ?.map { it.trim() }
                ?: defaultConfigStore.items,

            camps = Firebase.remoteConfig.getString("camps")
                .takeIf { it.isNotEmpty() }
                ?.split(";")
                ?.map { it.trim() }
                ?: defaultConfigStore.camps,

            locationName = Firebase.remoteConfig.getString("location_name")
                .takeIf { it.isNotEmpty() }
                ?: defaultConfigStore.locationName,

            phoneName = Firebase.remoteConfig.getString("phone_name")
                .takeIf { it.isNotEmpty() }
                ?: defaultConfigStore.phoneName
        )
    }

}