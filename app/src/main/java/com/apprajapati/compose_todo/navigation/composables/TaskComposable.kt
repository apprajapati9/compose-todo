package com.apprajapati.compose_todo.navigation.composables

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.apprajapati.compose_todo.util.Action
import com.apprajapati.compose_todo.util.Constants

fun NavGraphBuilder.taskComposable(navigateToListScreen: (Action) -> Unit) {
    composable(
        route = Constants.TASK_SCREEN,
        arguments = listOf(navArgument(Constants.TASK_ARG_KEY) {
            type = NavType.IntType
        })
    ) {

    }
}