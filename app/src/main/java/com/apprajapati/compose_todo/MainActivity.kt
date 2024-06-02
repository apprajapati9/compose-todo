package com.apprajapati.compose_todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.apprajapati.compose_todo.navigation.composables.SetupNavigation
import com.apprajapati.compose_todo.ui.theme.ComposetodoTheme
import com.apprajapati.compose_todo.ui.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    private val mViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //mViewModel = ViewModelProvider(this, SharedViewModel::class.java)

        setContent {
            ComposetodoTheme {
                navController = rememberNavController()
                SetupNavigation(navController = navController, mViewModel)
            }
        }
    }
}

