package com.nocamelstyle.campapp.screens.golden_poems

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

private val goldenPoems by lazy {
    listOf(
        GoldenPoem(
            "1 день",
            "Пётр, Апостол Иисуса Христа, пришельцам, рассеянным в Понте, Галатии, Каппадокии, Асии и Вифинии, избранным,",
            "1-е Петра 1:1"
        ),
        GoldenPoem(
            "2 день",
            "Благословен Бог и Отец Господа нашего Иисуса Христа, по великой Своей милости возродивший нас воскресением Иисуса Христа из мёртвых к упованию живому,",
            "1-е Петра 1:1"
        ),
        GoldenPoem(
            "3 день",
            "дабы испытанная вера ваша оказалась драгоценнее гибнущего, хотя и огнём испытываемого золота, к похвале и чести и славе в явление Иисуса Христа,",
            "1-е Петра 1:1"
        ),
        GoldenPoem(
            "4 день",
            "К сему-то спасению относились изыскания и исследования пророков, которые предсказывали о назначенной вам благодати,",
            "1-е Петра 1:1"
        ),
        GoldenPoem(
            "5 день",
            "Им открыто было, что не им самим, а нам служило то, что ныне проповедано вам благовествовавшими Духом Святым, посланным с небес, во что желают проникнуть Ангелы.",
            "1-е Петра 1:1"
        ),
        GoldenPoem(
            "6 день",
            "Послушанием истине через Духа, очистив души ваши к нелицемерному братолюбию, постоянно любите друг друга от чистого сердца,",
            "1-е Петра 1:1"
        )
    )
}

@Composable
fun GoldenPoemsScreen() {
    LazyColumn(Modifier.statusBarsPadding()) {
        items(goldenPoems) {
            GoldenPoem(it)
        }
    }
}

@Composable
fun GoldenPoem(goldenPoem: GoldenPoem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = goldenPoem.title,
                fontWeight = FontWeight.Bold,
            )
            Text(text = goldenPoem.poem)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = goldenPoem.link,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}