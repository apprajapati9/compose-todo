package com.apprajapati.compose_todo.data.repositories

import com.apprajapati.compose_todo.data.models.Task
import com.apprajapati.compose_todo.data.room.TodoDAO
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class TodoRepository @Inject constructor(private val toDoDao: TodoDAO) {

    val getAllTasks: Flow<List<Task>> = toDoDao.getAllTasks()
    val sortByLowPriority: Flow<List<Task>> = toDoDao.sortByLowPriority()
    val sortByHighPriority: Flow<List<Task>> = toDoDao.sortByHighPriority()

    fun getSelectedTask(taskId: Int): Flow<Task> {
        return toDoDao.getSelectedTask(taskId = taskId)
    }

    suspend fun addTask(Task: Task) {
        toDoDao.addTask(Task = Task)
    }

    suspend fun updateTask(Task: Task) {
        toDoDao.updateTask(Task = Task)
    }

    suspend fun deleteTask(Task: Task) {
        toDoDao.deleteTask(Task = Task)
    }

    suspend fun deleteAllTasks() {
        toDoDao.deleteAllTasks()
    }

    fun searchDatabase(searchQuery: String): Flow<List<Task>> {
        return toDoDao.searchDatabase(searchQuery = searchQuery)
    }

}