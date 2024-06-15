package com.apprajapati.compose_todo.ui.screens.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.apprajapati.compose_todo.R
import com.apprajapati.compose_todo.ui.screens.appbar.ListAppbar
import com.apprajapati.compose_todo.ui.viewmodels.SharedViewModel
import com.apprajapati.compose_todo.util.SearchAppbarState

@Composable
fun ListScreen(viewModel: SharedViewModel, navigateToTaskScreen: (taskId: Int) -> Unit) {

    val searchAppbarState: SearchAppbarState by viewModel.searchAppbarState
    val searchStringState: String by viewModel.searchTextState

    LaunchedEffect(key1 = true) {
        viewModel.getAllTasks()
    }

    val allTasks by viewModel.allTasks.collectAsState() //instead of passing allTasks.value from StateFlow, you can use "by" keyword in allTasks declaration

    Scaffold(topBar = {
        ListAppbar(viewModel, searchAppbarState, searchStringState)
    },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                ListContent(
                    tasks = allTasks, navigateToTaskScreen = navigateToTaskScreen
                )
            }

        }, floatingActionButton = {
            ListFAB(
                onFabClicked = navigateToTaskScreen
//                {
//                    Log.d("Ajay", "Clicked to add task")
//                    for (i in 1..5) {
//                        val task = Task(
//                            task =
//                            "Code on Leetcode task $i",
//                            taskDescription = "All about task $i",
//                            priority = Priority.MEDIUM
//                        )
//                        viewModel.insertTask(task)
//                    }
//                }
            )
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
@Preview
private fun ListScreenPreview() {
    // ListScreen(viewModel = (), navigateToTaskScreen = {})
}