package me.asiimwedismas.kmega_mono.ui.common_components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.asiimwedismas.kmega_mono.ui.theme.TypographyM2

@Composable
fun <T> StatementBody(
    modifier: Modifier = Modifier,
    items: List<T>,
    colors: (T) -> Color,
    amounts: (T) -> Long,
    amountsTotal: Long,
    circleLabel: String,
    rows: @Composable (T) -> Unit,
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Box(Modifier.padding(16.dp)) {
            val accountsProportion = items.extractProportions { amounts(it) }
            val circleColors = items.map { colors(it) }
            AnimatedCircle(
                proportions = accountsProportion,
                colors = circleColors,
                Modifier
                    .height(300.dp)
                    .align(Alignment.Center)
                    .fillMaxWidth()
            )
            Column(modifier = Modifier.align(Alignment.Center)) {
                Text(
                    text = circleLabel,
                    style = TypographyM2.body1,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = formatAmount(amountsTotal),
                    style = TypographyM2.h2,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Card(
            backgroundColor = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                items.forEach { rows(it) }
            }
        }
    }
}