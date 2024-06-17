package com.apprajapati.compose_todo.ui.screens.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.apprajapati.compose_todo.R
import com.apprajapati.compose_todo.ui.screens.appbar.ListAppbar
import com.apprajapati.compose_todo.ui.viewmodels.SharedViewModel
import com.apprajapati.compose_todo.util.Action
import com.apprajapati.compose_todo.util.SearchAppbarState

@Composable
fun ListScreen(
    action: Action,
    viewModel: SharedViewModel,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {

    LaunchedEffect(key1 = action) {
        viewModel.handleDatabaseAction(action = action)
    }

    val searchAppbarState: SearchAppbarState = viewModel.searchAppBarState
    val searchStringState: String = viewModel.searchTextState
//
//    LaunchedEffect(key1 = true) {
//        viewModel.getAllTasks()
//    }

    //val action by viewModel.action

    val allTasks by viewModel.allTasks.collectAsState() //instead of passing allTasks.value from StateFlow, you can use "by" keyword in allTasks declaration

    val searchedTasks by viewModel.searchedTasks.collectAsState()
    val sortState by viewModel.sortState.collectAsState()
    val lowPriorityTasks by viewModel.lowPriorityTasks.collectAsState()
    val highPriorityTasks by viewModel.highPriorityTasks.collectAsState()

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    DisplaySnackBar(
        snackBarHostState = snackBarHostState,
        onComplete = { viewModel.updateAction(action) },
        onUndoClicked = { viewModel.updateAction(action) },
        taskTitle = viewModel.title,
        action = action
    )

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            ListAppbar(viewModel, searchAppbarState, searchStringState)
        },
        content = { padding ->

            Column(modifier = Modifier.padding(padding)) {
                ListContent(
                    tasks = allTasks,
                    navigateToTaskScreen = navigateToTaskScreen,
                    searchedTasks = searchedTasks,
                    lowPriorityTasks = lowPriorityTasks,
                    highPriorityTasks = highPriorityTasks,
                    onSwipeToDelete = { action, task ->
                        viewModel.updateAction(newAction = action)
                        viewModel.updateTaskFields(selectedTask = task)
                        snackBarHostState.currentSnackbarData?.dismiss()
                    },
                    sortState = sortState,
                    searchAppBarState = searchAppbarState
                )
            }


        }, floatingActionButton = {
            ListFAB(onFabClicked = navigateToTaskScreen)
        })
}

@Composable
fun ListFAB(onFabClicked: (taskId: Int) -> Unit) {
    FloatingActionButton(onClick = {
        onFabClicked(-1)
    }, content = {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button_content_desc),
            tint = Color.Black
        )
    })
}

@Composable
fun DisplaySnackBar(
    snackBarHostState: SnackbarHostState,
    onComplete: (Action) -> Unit,
    onUndoClicked: (Action) -> Unit,
    taskTitle: String,
    action: Action
) {
    LaunchedEffect(key1 = action) {

        if (action != Action.NO_ACTION) {
            val snackBarResult = snackBarHostState.showSnackbar(
                message = setMessage(action, taskTitle),
                actionLabel = setActionLabel(action),
                duration = SnackbarDuration.Short
            )

            if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {
                onUndoClicked(Action.UNDO)
            } else if (snackBarResult == SnackbarResult.Dismissed || action != Action.DELETE) {
                onComplete(Action.NO_ACTION)
            }
        }

    }

}

private fun setMessage(action: Action, taskTitle: String): String {
    return when (action) {
        Action.DELETE_ALL -> "All tasks removed."
        else -> "${action.name}: $taskTitle"
    }
}


private fun setActionLabel(action: Action): String {
    return if (action.name == "DELETE") {
        "UNDO"
    } else {
        "OK"
    }
}

@Composable
@Preview
private fun ListScreenPreview() {
    // ListScreen(viewModel = (), navigateToTaskScreen = {})
}