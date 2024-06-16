package com.apprajapati.compose_todo.navigation.composables

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.apprajapati.compose_todo.ui.screens.list.ListScreen
import com.apprajapati.compose_todo.ui.viewmodels.SharedViewModel
import com.apprajapati.compose_todo.util.Constants.LIST_ARG_KEY
import com.apprajapati.compose_todo.util.Constants.LIST_SCREEN
import com.apprajapati.compose_todo.util.toAction

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    mViewModel: SharedViewModel
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARG_KEY) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->
        val action = navBackStackEntry.arguments!!.getString(LIST_ARG_KEY).toAction()
        Log.d("ListComposable", "listComposable: $action ")

        ListScreen(navigateToTaskScreen = navigateToTaskScreen, viewModel = mViewModel)
    }
}