package com.apprajapati.compose_todo.ui.screens.task

import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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

    val title: String = viewModel.title
    val description: String = viewModel.description
    val priority: Priority = viewModel.priority

    val context = LocalContext.current

    BackHandler {
        navigateToListScreen(Action.NO_ACTION)
    }

    Scaffold(topBar = {
        TaskAppbar(
            task = selectedTask,
            navigateToListScreen = { action ->
                Log.d("Action in TaskScreen", "TaskScreen: $action")
                if (action == Action.NO_ACTION) {
                    navigateToListScreen(action)
                } else {
                    if (viewModel.validateFields()) {
                        navigateToListScreen(action)
                    } else {
                        //ShowSnackBar(context = context, message = "Please enter a task title")
                    }
                }
            }
        )

    }, content = { paddingValues ->

        Surface(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())) {
            TaskContent(
                title = title,
                onTitleChange = {
                    viewModel.updateTitle(it)
                },
                onPrioritySelected = {
                    viewModel.updatePriority(it)
                },
                priority = priority,
                onDescriptionChange = {
                    viewModel.updateDescription(it)
                },
                description = description
            )
        }
    })

}


//Todo
@Composable
fun ShowSnackBar(context: Context, message: String) {
    Snackbar {
        Text(text = message)
    }
}