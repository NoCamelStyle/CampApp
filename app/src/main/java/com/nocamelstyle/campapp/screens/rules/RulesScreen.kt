package com.nocamelstyle.campapp.screens.rules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
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

private val allRules by lazy {
    listOf(
        Rule("Правила лагеря", "Что нельзя делать в лагере", "Любое описание лагерных правил"),
        Rule("Общелагерная игра 1 день", "Правила игры 'Кораблекрушение'", "Любое описание лагерных правил"),
        Rule("Общелагерная игра 2 день", "Правила игры 'Кораблекрушение'", "Любое описание игровых правил"),
        Rule("Общелагерная игра 3 день", "Правила игры 'Кораблекрушение'", "Любое описание игровых правил"),
        Rule("Общелагерная игра 4 день", "Правила игры 'Кораблекрушение'", "Любое описание игровых правил"),
        Rule("Общелагерная игра 5 день", "Правила игры 'Кораблекрушение'", "Любое описание игровых правил"),
        Rule("Общелагерная игра 6 день", "Правила игры 'Кораблекрушение'", "Любое описание игровых правил"),
    )
}

@Composable
fun RulesScreen(
    rules: List<Rule> = allRules,
    open: (Rule) -> Unit
) {
    LazyColumn(Modifier.systemBarsPadding()) {
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
                text = rule.rule
            )
        }
    }
}