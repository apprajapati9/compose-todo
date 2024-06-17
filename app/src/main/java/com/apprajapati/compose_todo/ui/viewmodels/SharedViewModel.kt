package com.apprajapati.compose_todo.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apprajapati.compose_todo.data.models.Priority
import com.apprajapati.compose_todo.data.models.Task
import com.apprajapati.compose_todo.data.repositories.DataStoreRepository
import com.apprajapati.compose_todo.data.repositories.TodoRepository
import com.apprajapati.compose_todo.util.Action
import com.apprajapati.compose_todo.util.Constants.MAX_TITLE_LENGTH
import com.apprajapati.compose_todo.util.RequestState
import com.apprajapati.compose_todo.util.SearchAppbarState
import com.apprajapati.compose_todo.util.TrailingIconState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: TodoRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {


    var action by mutableStateOf(Action.NO_ACTION)
        private set

    var id by mutableIntStateOf(0)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var priority by mutableStateOf(Priority.LOW)
        private set

    var searchAppBarState by mutableStateOf(SearchAppbarState.CLOSED)
        private set

    var searchTextState by mutableStateOf("")
        private set


    var searchAppbarCloseButtonState: MutableState<TrailingIconState> =
        mutableStateOf(TrailingIconState.CLOSE)

    private val _allTasks = MutableStateFlow<RequestState<List<Task>>>(RequestState.Idle)
    val allTasks: StateFlow<RequestState<List<Task>>> = _allTasks

    private val _searchedTasks =
        MutableStateFlow<RequestState<List<Task>>>(RequestState.Idle)
    val searchedTasks: StateFlow<RequestState<List<Task>>> = _searchedTasks

    private val _sortState =
        MutableStateFlow<RequestState<Priority>>(RequestState.Idle)
    val sortState: StateFlow<RequestState<Priority>> = _sortState

    init {
        getAllTasks()
    }

    fun searchDatabase(searchQuery: String) {
        _searchedTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.searchDatabase(searchQuery = "%$searchQuery%")
                    .collect { searchedTasks ->
                        _searchedTasks.value = RequestState.Success(searchedTasks)
                    }
            }
        } catch (e: Exception) {
            _searchedTasks.value = RequestState.Error(e)
        }
        searchAppBarState = SearchAppbarState.TRIGGERED
    }

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


    val lowPriorityTasks: StateFlow<List<Task>> =
        repository.sortByLowPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    val highPriorityTasks: StateFlow<List<Task>> =
        repository.sortByHighPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )


    private fun readSortState() {
        _sortState.value = RequestState.Loading
        try {
            viewModelScope.launch {
                dataStoreRepository.readSortState
                    .map { Priority.valueOf(it) }
                    .collect {
                        _sortState.value = RequestState.Success(it)
                    }
            }
        } catch (e: Exception) {
            _sortState.value = RequestState.Error(e)
        }
    }


    fun persistSortState(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistSortState(priority = priority)
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

    private fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val task = Task(
                task = title,
                taskDescription = description,
                priority = priority
            )
            repository.addTask(task)
        }
        searchAppBarState = SearchAppbarState.CLOSED
    }


    fun handleDatabaseAction(action: Action) {
        when (action) {
            Action.ADD -> {
                addTask()
                updateAction(Action.NO_ACTION)
            }

            Action.UPDATE -> {
                updateTask()
            }

            Action.DELETE -> {
                deleteTask()
            }

            Action.DELETE_ALL -> {
                deleteAllTask()
            }

            Action.UNDO -> {
                addTask()
                updateAction(Action.NO_ACTION)
            }

            else -> {
            }
        }
    }


    private fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(
                Task =
                Task(
                    id = id,
                    task = title,
                    taskDescription = description,
                    priority = priority
                )
            )
        }
    }

    private fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val task = Task(
                id = id,
                task = title,
                taskDescription = description,
                priority = priority
            )
            repository.updateTask(Task = task)
        }
    }

    fun deleteAllTask() {
        viewModelScope.launch {
            repository.deleteAllTasks()
        }
    }

    fun updateTaskFields(selectedTask: Task?) {
        if (selectedTask != null) {
            title = selectedTask.task
            description = selectedTask.taskDescription
            id = selectedTask.id
            priority = selectedTask.priority
        } else {
            title = ""
            description = ""
            id = 0
            priority = Priority.LOW
        }
    }

    fun updateTitle(newTitle: String) {
        if (newTitle.length <= MAX_TITLE_LENGTH) {
            title = newTitle
        }
    }

    fun validateFields(): Boolean {
        return title.isNotEmpty()
    }

    fun updateAction(newAction: Action) {
        action = newAction
    }

    fun updateDescription(newDescription: String) {
        description = newDescription
    }

    fun updatePriority(newPriority: Priority) {
        priority = newPriority
    }

    fun updateAppBarState(newState: SearchAppbarState) {
        searchAppBarState = newState
    }

    fun updateSearchText(newText: String) {
        searchTextState = newText
    }
    
}