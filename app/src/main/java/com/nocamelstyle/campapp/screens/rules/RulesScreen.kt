package com.nocamelstyle.campapp.screens.rules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val longRule = "Правила игры (оригинальное французское название: La règle du jeu) - французский сатирический комедийно-драматический фильм 1939 года режиссера Жана Ренуара. В актерский состав ансамбля входят Нора Грегор, Полетт Дюбост, Мила Парели, Марсель Далио, Жюльен Каретт, Ролан Тутен, Гастон Модо, Пьер Манье и Ренуар.\n" +
        "\n" +
        "Изображение Ренуаром мудрого, скорбного Октава закрепляет фаталистическое настроение этой задумчивой комедии нравов. В фильме изображены представители высшего класса французского общества и их слуги незадолго до начала Второй мировой войны, демонстрирующие свою моральную черствость накануне разрушения.\n" +
        "\n" +
        "В то время \"Правила игры\" был самым дорогим французским фильмом, снятым: Его первоначальный бюджет в 2,5 миллиона франков со временем увеличился до более чем 5 миллионов франков. Ренуар и кинематографист Жан Бачелет в 1939 году широко использовали глубокую фокусировку и длинные кадры, во время которых камера постоянно движется, сложные кинематографические приемы.\n" +
        "\n" +
        "Карьера Ренуара во Франции достигла своего апогея в 1939 году, и \"Правила игры\" ожидались с нетерпением. Однако его премьера была встречена критиками и зрителями с презрением и неодобрением. Ренуар сократил продолжительность фильма со 113 минут до 85, но даже тогда фильм стал критической и финансовой катастрофой. В октябре 1939 года он был запрещен французским правительством военного времени за \"нежелательное влияние на молодежь\".[1]\n" +
        "\n" +
        "В течение многих лет 85-минутная версия была единственной доступной; несмотря на это, ее репутация постепенно росла. Однако в 1956 году были обнаружены коробки с оригинальными материалами, и в том же году на Венецианском кинофестивале состоялась премьера реконструированной версии фильма, в которой отсутствовала лишь незначительная сцена из первой работы Ренуара. С тех пор \"Правила игры\" называют одним из величайших фильмов в истории кинематографа. Многочисленные кинокритики и режиссеры высоко оценили фильм, назвав его источником вдохновения для своих собственных работ. Это единственный фильм, занявший место среди десяти лучших в десятилетнем опросе критиков авторитетного сайта Sight & Sound (Британский институт кино) за каждое десятилетие с момента начала опроса в 1952 году по список 2012 года (в 2022 году он опустился до 13-го места)."

val allRules by lazy {
    listOf(
        Rule(1, "Правила лагеря", "Что нельзя делать в лагере", "Любое описание лагерных правил"),
        Rule(2, "Общелагерная игра 1 день", "Правила игры 'Кораблекрушение'", longRule),
        Rule(3, "Общелагерная игра 2 день", "Правила игры 'Кораблекрушение'", longRule),
        Rule(4, "Общелагерная игра 3 день", "Правила игры 'Кораблекрушение'", longRule),
        Rule(5, "Общелагерная игра 4 день", "Правила игры 'Кораблекрушение'", longRule),
        Rule(6, "Общелагерная игра 5 день", "Правила игры 'Кораблекрушение'", longRule),
        Rule(7, "Общелагерная игра 6 день", "Правила игры 'Кораблекрушение'", "Любое описание игровых правил"),
    )
}

@Composable
fun RulesScreen(
    rules: List<Rule> = allRules,
    open: (Rule) -> Unit
) {
    LazyColumn {
        items(rules) {
            RuleItem(it, open)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RuleItem(rule: Rule, open: (Rule) -> Unit) {
    Card(
        onClick = { open(rule) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = rule.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = rule.description,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = rule.rule,
                maxLines = 2
            )
        }
    }
}