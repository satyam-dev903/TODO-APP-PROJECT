package com.satyam15.todoapp.repository

import com.satyam15.todoapp.data.Todo
import com.satyam15.todoapp.data.TodoDao
import kotlinx.coroutines.flow.Flow

/**
 * Repository abstracts the access to multiple data sources.
 * It's not part of the Architecture Components libraries, but is a
 * best-practice for code separation and architecture.
 */
class TodoRepository(private val todoDao: TodoDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allTodos: Flow<List<Todo>> = todoDao.getAllTodos()

    suspend fun insert(todo: Todo) {
        todoDao.insertTodo(todo)
    }

    suspend fun delete(todo: Todo) {
        todoDao.deleteTodo(todo)
    }

    suspend fun updateStatus(id: Int, isDone: Boolean) {
        todoDao.updateTodoStatus(id, isDone)
    }
}