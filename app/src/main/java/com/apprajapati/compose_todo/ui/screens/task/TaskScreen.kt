package com.apprajapati.compose_todo.ui.screens.task

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.apprajapati.compose_todo.data.models.Priority
import com.apprajapati.compose_todo.data.models.Task
import com.apprajapati.compose_todo.ui.viewmodels.SharedViewModel
import com.apprajapati.compose_todo.util.Action

@Composable
fun TaskScreen(
    selectedTask: Task?,
    viewModel: SharedViewModel,
    navigateToListScreen: (Action) -> Unit
) {

    val title: String by viewModel.title
    val description: String by viewModel.description
    val priority: Priority by viewModel.priority



    Scaffold(topBar = {
        TaskAppbar(
            selectedTask,
            navigateToListScreen = navigateToListScreen
        )

    }, content = { paddingValues ->

        //PaddingValues(paddingValues.calculateBottomPadding())
        Surface(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())) {
            TaskContent(
                title = title,
                onTitleChange = {
                    viewModel.title.value = it
                },
                onPrioritySelected = {
                    viewModel.priority.value = it
                },
                priority = priority,
                onDescriptionChange = {
                    viewModel.description.value = it
                },
                description = description
            )
        }
    })

}