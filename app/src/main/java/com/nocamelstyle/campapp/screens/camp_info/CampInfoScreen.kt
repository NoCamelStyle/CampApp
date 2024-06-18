package com.nocamelstyle.campapp.screens.camp_info

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import java.util.Locale


@Composable
fun CampInfoScreen() {

    val context = LocalContext.current
    val paragraphPadding = 10.dp

    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {

        Spacer(modifier = Modifier.height(paragraphPadding))

        Text(text = "С собой взять:", fontWeight = FontWeight.Bold)
        Text(
            text = "• кепку\n" +
                    "• шорты (ниже колен)\n" +
                    "• Библию\n" +
                    "• постельное\n" +
                    "• подушку\n" +
                    "• полотенце"
        )

        Spacer(modifier = Modifier.height(paragraphPadding))

        Text(text = "По всем вопросам:", fontWeight = FontWeight.Bold)
        TextButton(
            onClick = { context.callToAdmin() },
            contentPadding = PaddingValues(0.dp),
            shape = RectangleShape
        ) {
            Text(
                text = "+7(900)185-80-11 (Константин)",
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(paragraphPadding))

        TextButton(
            onClick = { context.openCampLocationInMapApp() },
            contentPadding = PaddingValues(0.dp),
            shape = RectangleShape
        ) {
            Text(
                text = "Лагерь проходит на базе Нива в Благодатном (нажми чтобы открыть)",
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(paragraphPadding))
        Text(text = "Когда проходит:", fontWeight = FontWeight.Bold)
        Text(
            text = "• 1 смена (11-15 лет) с 08.06-18.06\n" +
                    "• 2 смена (11-15 лет) с 08.06-18.06\n" +
                    "• 3 смена (11-15 лет) с 08.06-18.06\n" +
                    "• 4 смена (11-15 лет) с 08.06-18.06\n"
        )

    }
}

private fun Context.openCampLocationInMapApp() {
    val uri = String.format (Locale.ENGLISH, "geo:%f,%f", 36f, 42.2f)
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    startActivity(intent)
}

private fun Context.callToAdmin() {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.setData(Uri.parse("tel:89001858011"))
    startActivity(intent)
}