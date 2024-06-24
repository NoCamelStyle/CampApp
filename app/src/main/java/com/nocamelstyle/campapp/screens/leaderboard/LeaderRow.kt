package com.nocamelstyle.campapp.screens.leaderboard

import kotlin.random.Random

data class LeaderRow(
    val teamName: String,
    val daysPoints: List<Int>
) {
    val allPoints get() = daysPoints.sum()
}

val leaderboardTable by lazy {
    listOf(
        LeaderRow("Синие", listOf(getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue())),
        LeaderRow("Розовые", listOf(getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue())),
        LeaderRow("Белые", listOf(getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue())),
        LeaderRow("Длинное название", listOf(getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue(), getRandomValue()))
    )
}

private fun getRandomValue(): Int {
    return Random.nextInt(0, 20)
}