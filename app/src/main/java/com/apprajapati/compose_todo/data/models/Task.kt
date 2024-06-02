package com.apprajapati.compose_todo.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.apprajapati.compose_todo.util.Constants.DATABASE_TABLE

@Entity(tableName = DATABASE_TABLE)
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val task: String,
    val taskDescription: String,
    val priority: Priority
)
