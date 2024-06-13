package com.apprajapati.compose_todo.navigation.composables

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.apprajapati.compose_todo.ui.screens.task.TaskScreen
import com.apprajapati.compose_todo.ui.viewmodels.SharedViewModel
import com.apprajapati.compose_todo.util.Action
import com.apprajapati.compose_todo.util.Constants
import com.apprajapati.compose_todo.util.Constants.TASK_ARG_KEY

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit,
    mViewModel: SharedViewModel
) {
    composable(
        route = Constants.TASK_SCREEN,
        arguments = listOf(navArgument(TASK_ARG_KEY) {
            type = NavType.IntType
        })
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARG_KEY)
        Log.d("TaskComposable", "taskComposable: $taskId ")

        TaskScreen(navigateToListScreen = navigateToListScreen)
    }
}