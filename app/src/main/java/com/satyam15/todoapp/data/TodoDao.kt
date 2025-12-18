package com.satyam15.todoapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    // 'suspend' makes this function Coroutine-friendly (runs in background).
    // OnConflictStrategy.REPLACE ensures if we add a Todo with an existing ID, it updates it.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    // A simple SQL query to get all items.
    // We sort them by ID in descending order so new tasks appear at the top.
    @Query("SELECT * FROM todo_table ORDER BY id DESC")
    fun getAllTodos(): Flow<List<Todo>>

    @Query("UPDATE todo_table SET isDone = :isDone WHERE id = :id")
    suspend fun updateTodoStatus(id: Int, isDone: Boolean)
}