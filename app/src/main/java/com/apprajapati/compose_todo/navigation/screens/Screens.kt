package com.apprajapati.compose_todo.navigation.screens

import androidx.navigation.NavHostController
import com.apprajapati.compose_todo.util.Action
import com.apprajapati.compose_todo.util.Constants.LIST_SCREEN

class Screens(navController: NavHostController) {
    
    val list: (Int) -> Unit = { taskId ->
        navController.navigate(route = "task/$taskId")
    }

    val task: (Action) -> Unit = { action ->
        navController.navigate(route = "list/${action}") {
            popUpTo(LIST_SCREEN) {
                inclusive = true
            }
        }
    }
}