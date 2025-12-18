package com.satyam15.todoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Entity tells Room that this class will be a table in our SQLite database.
 * 'tableName' allows us to give the table a specific name.
 */
@Entity(tableName = "todo_table")
data class Todo(

    // Every table needs a PrimaryKey to uniquely identify each row.
    // autoGenerate = true means Room will handle the ID (1, 2, 3...) for us.
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,

    val isDone: Boolean = false,

    val priority: String // e.g., "High", "Medium", "Low"
)