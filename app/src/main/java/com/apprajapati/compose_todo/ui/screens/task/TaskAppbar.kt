package com.apprajapati.compose_todo.ui.screens.task

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.apprajapati.compose_todo.data.models.Priority
import com.apprajapati.compose_todo.data.models.Task
import com.apprajapati.compose_todo.util.Action

@Composable
fun TaskAppbar(task: Task?, navigateToListScreen: (Action) -> Unit) {

    if (task != null) {
        ExistingTaskAppbar(selectedTask = task, navigateToListScreen = navigateToListScreen)
    } else {
        NewTaskAppbar(navigateToListScreen = navigateToListScreen)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskAppbar(navigateToListScreen: (Action) -> Unit) {
    TopAppBar(
        navigationIcon = {
            BackActionButton(onBackButtonClicked = navigateToListScreen)
        },
        title = {
            Text(text = "Add a new Task", color = MaterialTheme.colorScheme.onBackground)
        },
        actions = {
            AddActionButton(onAddButtonClicked = navigateToListScreen)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExistingTaskAppbar(
    selectedTask: Task,
    navigateToListScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            CloseActionButton(onCloseClicked = navigateToListScreen)

        },
        title = {
            Text(
                text = selectedTask.task,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            DeleteActionButton(onDeleteClicked = navigateToListScreen)
            UpdateActionButton(onUpdateClicked = navigateToListScreen)
        }
    )
}

@Composable
fun CloseActionButton(onCloseClicked: (Action) -> Unit) {
    IconButton(onClick = {
        onCloseClicked(Action.NO_ACTION)
    }) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Close Button"
        )
    }
}


@Composable
fun DeleteActionButton(onDeleteClicked: (Action) -> Unit) {
    IconButton(onClick = {
        onDeleteClicked(Action.DELETE)
    }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = "Delete Button"
        )
    }
}


@Composable
fun UpdateActionButton(onUpdateClicked: (Action) -> Unit) {
    IconButton(onClick = {
        onUpdateClicked(Action.UPDATE)
    }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = "Update Button"
        )
    }
}

@Composable
fun BackActionButton(onBackButtonClicked: (Action) -> Unit) {
    IconButton(onClick = {
        onBackButtonClicked(Action.NO_ACTION)
    }) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back Arrow Button"
        )
    }
}

@Composable
fun AddActionButton(onAddButtonClicked: (Action) -> Unit) {
    IconButton(onClick = {
        onAddButtonClicked(Action.ADD)
    }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = "Add Task Button"
        )
    }
}


@Composable
@Preview
fun TaskAppbarPreview() {
    NewTaskAppbar(navigateToListScreen = { })
}

@Composable
@Preview
fun ExistingTaskAppbarPreview() {
    ExistingTaskAppbar(
        selectedTask = Task(id = 0, task = "whatever", taskDescription = "nothing", Priority.HIGH),
        navigateToListScreen = { })
}