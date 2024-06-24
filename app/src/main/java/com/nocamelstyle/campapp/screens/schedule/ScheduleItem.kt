package com.nocamelstyle.campapp.screens.schedule

import java.util.Date

class ScheduleItem(
    val timeStart: Date,
    val timeEnd: Date,
    val title: String
)

val scheduleList by lazy {
    listOf(
        ScheduleItem(
            createDate(9, 0),
            createDate(9, 15),
            "Зарядка"
        ),
        ScheduleItem(
            createDate(9, 20),
            createDate(9, 35),
            "Завтрак в группе"
        ),
        ScheduleItem(
            createDate(9, 40),
            createDate(10, 0),
            "Линейка"
        ),
        ScheduleItem(
            createDate(10, 0),
            createDate(11, 0),
            "Библейский урок"
        ),
        ScheduleItem(
            createDate(11, 0),
            createDate(11, 30),
            "Игры/Спорт"
        ),
        ScheduleItem(
            createDate(11, 30),
            createDate(12, 30),
            "Кружки"
        ),
        ScheduleItem(
            createDate(12, 30),
            createDate(13, 0),
            "ОБЕД"
        ),
        ScheduleItem(
            createDate(13, 0),
            createDate(13, 30),
            "Свободное время"
        ),
        ScheduleItem(
            createDate(13, 30),
            createDate(14, 0),
            "Пение"
        ),
        ScheduleItem(
            createDate(14, 0),
            createDate(15, 15),
            "Общелагерная игра"
        ),
        ScheduleItem(
            createDate(15, 30),
            createDate(23, 59),
            "Вечерний огонек"
        )
    )
}

fun createDate(hoursValue: Int, minutesValue: Int): Date {
    return Date().apply {
        hours = hoursValue
        minutes = minutesValue
    }
}