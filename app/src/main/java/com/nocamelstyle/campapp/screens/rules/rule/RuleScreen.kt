package com.nocamelstyle.campapp.screens.rules.rule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nocamelstyle.campapp.screens.rules.Rule

@Composable
fun RuleScreen(rule: Rule) {
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 24.dp)
            .padding(bottom = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = rule.title,
            fontSize = 24.sp,
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