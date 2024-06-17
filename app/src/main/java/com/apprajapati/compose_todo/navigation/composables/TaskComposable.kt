package com.apprajapati.compose_todo.navigation.composables

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.apprajapati.compose_todo.ui.screens.task.TaskScreen
import com.apprajapati.compose_todo.ui.viewmodels.SharedViewModel
import com.apprajapati.compose_todo.util.Action
import com.apprajapati.compose_todo.util.Constants.TASK_ARG_KEY
import com.apprajapati.compose_todo.util.Constants.TASK_SCREEN

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit,
    mViewModel: SharedViewModel
) {
    composable(
        route = TASK_SCREEN,
        arguments = listOf(navArgument(TASK_ARG_KEY) {
            type = NavType.IntType
        }),
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(
                    durationMillis = 300
                )
            )
        }
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARG_KEY)
        Log.d("TaskComposable", "taskComposable: $taskId ")

        LaunchedEffect(key1 = taskId) {
            mViewModel.getSelectedTask(taskId = taskId)
        }


        val selectedTask by mViewModel.selectedTask.collectAsState()

        LaunchedEffect(key1 = selectedTask) {
            if (selectedTask != null || taskId == -1) {
                mViewModel.updateTaskFields(selectedTask)
            }
        }

        TaskScreen(
            selectedTask = selectedTask,
            viewModel = mViewModel,
            navigateToListScreen = navigateToListScreen
        )
    }
}