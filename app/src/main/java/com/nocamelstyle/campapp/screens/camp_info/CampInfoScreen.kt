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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import java.util.Locale

@Composable
fun CampInfoScreen(
    phone: String,
    location: Pair<Double, Double>,
    items: List<String>,
    camps: List<String>,
    locationName: String,
    phoneName: String
) {

    val context = LocalContext.current
    val paragraphPadding = 10.dp

    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {

        Spacer(modifier = Modifier.height(paragraphPadding))

        Text(text = "С собой взять:", fontWeight = FontWeight.Bold)

        val itemsList = items.map { "• $it" }.joinToString("\n")
        Text(text = itemsList)

        Spacer(modifier = Modifier.height(paragraphPadding))

        Text(text = "По всем вопросам:", fontWeight = FontWeight.Bold)
        TextButton(
            onClick = { context.callToAdmin() },
            contentPadding = PaddingValues(0.dp),
            shape = RectangleShape
        ) {
            Text(
                text = "$phone ($phoneName)",
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(paragraphPadding))

        TextButton(
            onClick = { context.openCampLocationInMapApp(location.first, location.second) },
            contentPadding = PaddingValues(0.dp),
            shape = RectangleShape
        ) {
            Text(
                text = locationName,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(paragraphPadding))
        Text(text = "Когда проходит:", fontWeight = FontWeight.Bold)

        val campSchedule = camps.map { "• $it" }.joinToString("\n")
        Text(text = campSchedule)

    }
}

private fun Context.openCampLocationInMapApp(latitude: Double, longitude: Double) {
    val uri = String.format (Locale.ENGLISH, "geo:%f,%f", latitude.toFloat(), longitude.toFloat())
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    startActivity(intent)
}

private fun Context.callToAdmin() {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.setData(Uri.parse("tel:89001858011"))
    startActivity(intent)
}