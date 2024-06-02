package com.apprajapati.compose_todo.navigation.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.apprajapati.compose_todo.navigation.screens.Screens
import com.apprajapati.compose_todo.ui.viewmodels.SharedViewModel
import com.apprajapati.compose_todo.util.Constants.LIST_SCREEN

@Composable
fun SetupNavigation(navController: NavHostController, mViewModel: SharedViewModel) {

    val screen = remember(navController) {
        Screens(navController)
    }

    NavHost(navController = navController, startDestination = LIST_SCREEN) {
        listComposable(navigateToTaskScreen = screen.task, mViewModel)
        taskComposable(navigateToListScreen = screen.list)
    }
}