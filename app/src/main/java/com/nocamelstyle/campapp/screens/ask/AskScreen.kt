package com.nocamelstyle.campapp.screens.ask

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


@Composable
fun AskScreen() {
    val context = LocalContext.current
    var question by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = question,
            placeholder = {
                Text(text = "Вопрос")
            },
            onValueChange = { question = it },
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { context.sendAsk(question) }
        ) {
            Text(text = "Задать вопрос")
        }
    }
}

private fun Context.sendAsk(message: String) {
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.setData(Uri.parse("mailto:andreysxkormachenko@gmail.com")) // only email apps should handle this
    intent.putExtra(Intent.EXTRA_SUBJECT, "Вопрос")
    intent.putExtra(Intent.EXTRA_TEXT, message)
    runCatching { startActivity(intent) }
}