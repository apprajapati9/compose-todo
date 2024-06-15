package com.apprajapati.compose_todo.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apprajapati.compose_todo.data.models.Priority

@Composable
fun PriorityDropDown(
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    val angle: Float by animateFloatAsState(targetValue = if (expanded) 180f else 0f, label = "")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable {
                expanded = true
            }
            .border(
                shape = RoundedCornerShape(4.dp),
                width = 1.dp,
                color = Color.Black.copy(alpha = 0.5f)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier
                .size(60.dp)
                .weight(1f)
                .padding(5.dp)
        ) {
            drawCircle(color = priority.color)
        }
        Text(
            modifier = Modifier
                .weight(8f)
                .padding(5.dp),
            text = priority.name,
            style = MaterialTheme.typography.titleMedium
        )

        IconButton(
            modifier = Modifier
                .alpha(0.6f)
                .rotate(degrees = angle)
                .weight(1.5f),
            onClick = {
                expanded = true
            }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Drop Down arrow Icon"
            )
        }
        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.92f),
            expanded = expanded,
            onDismissRequest = {
                expanded = false

            }) {
            DropdownMenuItem(
                text = {
                    Text(text = Priority.LOW.name)
                },
                onClick = {
                    expanded = false
                    onPrioritySelected(Priority.LOW)
                })

            DropdownMenuItem(
                text = { Text(text = Priority.HIGH.name) },
                onClick = {
                    expanded = false
                    onPrioritySelected(Priority.HIGH)
                })

            DropdownMenuItem(
                text = { Text(text = Priority.MEDIUM.name) },
                onClick = {
                    expanded = false
                    onPrioritySelected(Priority.MEDIUM)
                })
        }
    }
}


@Preview
@Composable
fun PreviewPriorityDropDown() {
    PriorityDropDown(priority = Priority.HIGH) {

    }
}