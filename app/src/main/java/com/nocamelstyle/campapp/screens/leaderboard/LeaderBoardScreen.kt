package com.nocamelstyle.campapp.screens.leaderboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.nocamelstyle.campapp.ui.conditional

@Composable
fun LeaderboardScreen() {
    Column(Modifier.systemBarsPadding()) {
        //range picker,
        val columns = leaderboard.first().daysPoints.indices.map { index ->
            leaderboard.map { it.daysPoints[index].toString() }
        }
        LazyRow {
            item {
                Spacer(modifier = Modifier.width(24.dp))
                LeaderValue(
                    listOf("Отряд") + leaderboard.map { it.teamName },
                    isTitle = true,
                    isFinal = false,
                    leaderboard.maxBy { it.allPoints }.teamName
                )
            }
            items(columns) {
                LeaderValue(listOf("День") + it, isTitle = false, isFinal = false, null)
            }
            item {
                LeaderValue(
                    listOf("Всего") + leaderboard.map { it.allPoints.toString() },
                    isTitle = false,
                    isFinal = true,
                    null
                )
            }
        }
    }
}

@Composable
private fun LeaderValue(items: List<String>, isTitle: Boolean, isFinal: Boolean, whoIsKing: String?) {
    var minWidth by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current
    Column(Modifier.padding(end = 10.dp)) {
        items.forEach {
            Card(
                modifier =
                (if (isTitle) Modifier.conditional(minWidth != 0.dp) { widthIn(min = minWidth) } else Modifier.width(
                    60.dp
                ))
                    .conditional(isTitle) {
                        onGloballyPositioned {
                            val width = with(density) {
                                it.size.width.toDp()
                            }
                            if (width > minWidth)
                                minWidth = width
                        }
                    },
                colors =
                if (isFinal) CardDefaults.cardColors(MaterialTheme.colorScheme.outline)
                else CardDefaults.cardColors()
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = it,
                        modifier = Modifier
                            .conditional(isTitle) {
                                padding(horizontal = 6.dp)
                            }
                            .padding(vertical = 16.dp)
                            .align(Alignment.Center),
                        color = if (isFinal) Color.White else Color.Unspecified
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}