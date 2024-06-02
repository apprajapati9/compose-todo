package com.apprajapati.compose_todo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.apprajapati.compose_todo.data.models.Task
import com.apprajapati.compose_todo.data.room.TodoDAO

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun toDoDao(): TodoDAO

}