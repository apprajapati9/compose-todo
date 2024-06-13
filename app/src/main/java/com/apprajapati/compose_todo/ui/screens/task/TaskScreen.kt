package com.apprajapati.compose_todo.ui.screens.task

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.apprajapati.compose_todo.util.Action

@Composable
fun TaskScreen(
    navigateToListScreen: (Action) -> Unit
) {

    Scaffold(topBar = {

    }) { paddingValues ->
        PaddingValues(paddingValues.calculateTopPadding())
        TaskAppbar(navigateToListScreen = navigateToListScreen)
    }
}