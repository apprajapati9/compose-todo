package com.apprajapati.compose_todo.ui.screens.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apprajapati.compose_todo.data.models.Priority
import com.apprajapati.compose_todo.data.models.Task
import com.apprajapati.compose_todo.ui.components.PriorityItem
import com.apprajapati.compose_todo.ui.theme.LARGE_PADDING

@Composable
fun ListContent(tasks: List<Task>, navigateToTaskScreen: (taskId: Int) -> Unit) {

    LazyColumn {

        item(contentType = tasks) {
            for (task in tasks) {
                TaskItem(todoTask = task) {
                    navigateToTaskScreen(it)
                }
            }
        }


    }
}

@Composable
fun TaskItem(todoTask: Task, navigateToTaskScreen: (taskId: Int) -> Unit) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 1.dp,
        tonalElevation = 1.dp,
        onClick = {
            navigateToTaskScreen(todoTask.id)
        }) {

        Column(
            modifier = Modifier
                .padding(all = LARGE_PADDING)
                .fillMaxWidth()
        ) {

            Row(
                Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.weight(0.8f),
                    text = todoTask.task,
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Box(
                    modifier = Modifier
                        .weight(0.1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    PriorityItem(priority = todoTask.priority, false)
                }

            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = todoTask.taskDescription,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

        }

    }

}

@Preview
@Composable
fun TaskPreview() {
    TaskItem(
        todoTask = Task(
            priority = Priority.HIGH,
            task = "Do coding everyday",
            taskDescription = "C++ algorithms and Kotlin"
        )
    ) {

    }
}