package com.apprajapati.compose_todo.data.models

import androidx.compose.ui.graphics.Color
import com.apprajapati.compose_todo.ui.theme.HighPriorityColor
import com.apprajapati.compose_todo.ui.theme.LowPriorityColor
import com.apprajapati.compose_todo.ui.theme.MediumPriorityColor

enum class Priority(val color: Color) {
    HIGH(color = HighPriorityColor),
    MEDIUM(color = MediumPriorityColor),
    LOW(color = LowPriorityColor),
    NONE(color = Color.White)
}