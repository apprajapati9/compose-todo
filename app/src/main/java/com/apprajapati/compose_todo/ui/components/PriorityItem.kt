package com.apprajapati.compose_todo.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apprajapati.compose_todo.data.models.Priority
import com.apprajapati.compose_todo.ui.theme.PRIORITY_INDICATOR_SIZE
import com.apprajapati.compose_todo.ui.theme.Typography

@Composable
fun PriorityItem(priority: Priority, isShowText: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Canvas(modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)) {
            drawCircle(color = priority.color)
        }
        if (isShowText) {
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = priority.name,
                style = Typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

    }
}

@Composable
@Preview
fun priortyPreview() {
    PriorityItem(priority = Priority.HIGH, true)
}

