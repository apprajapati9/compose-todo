package com.apprajapati.compose_todo.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apprajapati.compose_todo.data.models.Task
import com.apprajapati.compose_todo.data.repositories.TodoRepository
import com.apprajapati.compose_todo.util.RequestState
import com.apprajapati.compose_todo.util.SearchAppbarState
import com.apprajapati.compose_todo.util.TrailingIconState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel() {


    var searchAppbarState: MutableState<SearchAppbarState> =
        mutableStateOf(SearchAppbarState.CLOSED)
    var searchTextState: MutableState<String> = mutableStateOf("")

    var searchAppbarCloseButtonState: MutableState<TrailingIconState> =
        mutableStateOf(TrailingIconState.CLOSE)

    private val _allTasks = MutableStateFlow<RequestState<List<Task>>>(RequestState.Idle)
    val allTasks: StateFlow<RequestState<List<Task>>> = _allTasks


    fun getAllTasks() {
        _allTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.getAllTasks.collect {
                    _allTasks.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _allTasks.value = RequestState.Error(e)
        }

    }

    fun insertTask(task: Task) {
        viewModelScope.launch {
            repository.addTask(task)
        }
    }

    fun deleteAllTask() {
        viewModelScope.launch {
            repository.deleteAllTasks()
        }
    }
}