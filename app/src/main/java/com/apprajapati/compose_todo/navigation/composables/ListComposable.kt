package com.apprajapati.compose_todo.navigation.composables

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.apprajapati.compose_todo.ui.screens.list.ListScreen
import com.apprajapati.compose_todo.ui.viewmodels.SharedViewModel
import com.apprajapati.compose_todo.util.Constants.LIST_ARG_KEY
import com.apprajapati.compose_todo.util.Constants.LIST_SCREEN

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    mViewModel: SharedViewModel
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARG_KEY) {
            type = NavType.StringType
        })
    ) {
        ListScreen(mViewModel, navigateToTaskScreen = navigateToTaskScreen)
    }
}