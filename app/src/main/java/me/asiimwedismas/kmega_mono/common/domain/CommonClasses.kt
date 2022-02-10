package me.asiimwedismas.kmega_mono.common.domain


import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class ReportCategory(
    val name: String,
    val amount: Long,
    val color: Color
)