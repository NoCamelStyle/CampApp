package com.nocamelstyle.campapp.screens.schedule

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ScheduleScreen() {
    LazyColumn(Modifier.systemBarsPadding()) {
        items(scheduleList) {
            ScheduleView(item = it)

        }
    }
}

private val timeFormat by lazy {
    SimpleDateFormat("HH:mm", Locale.getDefault())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleView(item: ScheduleItem) {
    val isCurrentItem = Date().time in item.timeStart.time..item.timeEnd.time
    Card(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 6.dp),
        colors =
        if (isCurrentItem) CardDefaults.cardColors(MaterialTheme.colorScheme.outline)
        else CardDefaults.cardColors()
    ) {
        Text(
            text = "${timeFormat.format(item.timeStart)} - ${timeFormat.format(item.timeEnd)} : ${item.title}",
            modifier = Modifier.padding(16.dp),
            color = if (isCurrentItem) Color.White else Color.Unspecified
        )
    }
}