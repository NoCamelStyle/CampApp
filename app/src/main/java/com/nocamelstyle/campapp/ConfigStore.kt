package com.nocamelstyle.campapp

class ConfigStore(
    val email: String,
    val phone: String,
    val location: Pair<Double, Double>,
    val items: List<String>,
    val camps: List<String>,
    val locationName: String,
    val phoneName: String
)

val defaultConfigStore = ConfigStore(
    email = "andreysxkormachenko@gmail.com",
    phone = "+79001858010",
    location = Pair(33.7, 42.4),
    items = listOf(
        "кепку",
        "шорты (ниже колен)",
        "Библию",
        "постельное",
        "подушку",
        "полотенце"
    ),
    camps = listOf(
        "1 смена (11-15 лет) с 08.06-18.06",
        "2 смена (11-15 лет) с 08.06-18.06",
        "3 смена (11-15 лет) с 08.06-18.06",
        "4 смена (11-15 лет) с 08.06-18.06"
    ),
    locationName = "Лагерь проходит на базе Нива в Благодатном (нажми чтобы открыть)",
    phoneName = "Андрей"
)