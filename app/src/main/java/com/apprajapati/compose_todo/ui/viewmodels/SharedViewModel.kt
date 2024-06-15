package com.apprajapati.compose_todo.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apprajapati.compose_todo.data.models.Priority
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

    val id: MutableState<Int> = mutableStateOf(0)
    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val priority: MutableState<Priority> = mutableStateOf(Priority.LOW)


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

    private val _selectedTask: MutableStateFlow<Task?> = MutableStateFlow(null)
    val selectedTask: StateFlow<Task?> get() = _selectedTask


    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            repository.getSelectedTask(taskId = taskId).collect { task ->
                _selectedTask.value = task
            }
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

    fun updateTaskFields(selectedTask: Task?) {
        if (selectedTask != null) {
            title.value = selectedTask.task
            description.value = selectedTask.taskDescription
            id.value = selectedTask.id
            priority.value = selectedTask.priority
        } else {
            title.value = ""
            description.value = ""
            id.value = 0
            priority.value = Priority.LOW
        }
    }
}