package com.apprajapati.compose_todo.ui.screens.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apprajapati.compose_todo.data.models.Priority
import com.apprajapati.compose_todo.ui.components.PriorityDropDown

@Composable
fun TaskContent(
    title: String,
    onTitleChange: (String) -> Unit,
    priority: Priority,
    description: String,
    onDescriptionChange: (String) -> Unit,
    onPrioritySelected: (Priority) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 15.dp, end = 15.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = {
                onTitleChange(it)
            },
            label = {
                Text(text = "Title")
            },
            textStyle = MaterialTheme.typography.titleLarge,
            singleLine = true,

            )

        HorizontalDivider(
            Modifier.size(2.dp)
        )

        PriorityDropDown(priority = priority, onPrioritySelected = onPrioritySelected)

        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = description,
            onValueChange = { onDescriptionChange(it) },
            label = {
                Text(text = "Description")
            },
            textStyle = MaterialTheme.typography.titleMedium,
        )

    }
}


@Preview
@Composable
fun TaskContentPreview() {
    TaskContent(
        title = "Titlte",
        onTitleChange = {},
        priority = Priority.HIGH,
        description = "description",
        onDescriptionChange = {}
    ) {

    }
}