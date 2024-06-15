package com.nocamelstyle.campapp.screens.songs.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nocamelstyle.campapp.screens.songs.Song
import com.nocamelstyle.campapp.screens.songs.songs

@Composable
fun SongsListScreen() {
    var searchToken by remember {
        mutableStateOf("")
    }

    Column(Modifier.systemBarsPadding()) {
        TextField(
            value = searchToken,
            onValueChange = { searchToken = it },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
        )
        LazyColumn(Modifier.weight(1f)) {
            items(songs.filter { it.song.contains(searchToken, ignoreCase = true) }) {
                SongItem(song = it)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongItem(song: Song) {
    Card(
        onClick = {},
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Text(
            text = song.title,
            modifier = Modifier.padding(16.dp)
        )
    }
}