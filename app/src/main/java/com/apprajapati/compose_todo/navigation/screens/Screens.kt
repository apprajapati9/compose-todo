package com.apprajapati.compose_todo.navigation.screens

import androidx.navigation.NavController
import com.apprajapati.compose_todo.util.Action
import com.apprajapati.compose_todo.util.Constants.LIST_SCREEN

class Screens(navController: NavController) {
    val list: (Action) -> Unit = {
        navController.navigate("list/${it.name}") {
            popUpTo(LIST_SCREEN) {
                inclusive = true
            }
        }
    }

    val task: (Int) -> Unit = {
        navController.navigate("task/$it")
    }
}