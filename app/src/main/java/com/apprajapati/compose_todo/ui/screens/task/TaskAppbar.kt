package com.apprajapati.compose_todo.ui.screens.task

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.apprajapati.compose_todo.util.Action

@Composable
fun TaskAppbar(navigateToListScreen: (Action) -> Unit) {
    NewTaskAppbar(navigateToListScreen = navigateToListScreen)
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
    NewTaskAppbar(navigateToListScreen = {  })
}