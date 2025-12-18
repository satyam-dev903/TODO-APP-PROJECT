package com.satyam15.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.satyam15.todoapp.data.Todo
import com.satyam15.todoapp.data.TodoDatabase
import com.satyam15.todoapp.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * We use AndroidViewModel because it provides the Application context,
 * which we need to initialize the database.
 */
class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TodoRepository
    val allTodos: LiveData<List<Todo>>

    init {
        // 1. Get DAO from Database
        val dao = TodoDatabase.getDatabase(application).todoDao()
        // 2. Initialize Repository
        repository = TodoRepository(dao)
        // 3. Convert Flow to LiveData so the UI can observe it easily
        allTodos = repository.allTodos.asLiveData()
    }

    /**
     * viewModelScope.launch starts a Coroutine.
     * This ensures the database work doesn't block the UI (Main Thread).
     */
    fun insertTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(todo)
    }

    fun deleteTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(todo)
    }

    fun updateTodoStatus(id: Int, isDone: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateStatus(id, isDone)
    }
}


/*
    Mentor Explanation: Why viewModelScope and Dispatchers.IO?
        viewModelScope: If the user leaves the screen while a task is being saved,
        the viewModelScope automatically cancels the task.
         This prevents Memory Leaks and wasted processing power.

    Dispatchers.IO: Android will crash if you try to do database work
        on the "Main Thread" (the thread that draws the UI).
        Dispatchers.IO tells the phone: "Hey, do this work in the
         background where the disk operations happen."

    asLiveData(): We take the Flow from the repository and turn it into LiveData. LiveData is
        "lifecycle-aware," meaning it only sends updates to
         the UI if the Fragment is actually visible on the screen.

    Progress Check: The "Wiring" is Complete
    At this point, you have built the entire Data Pipeline: Database → DAO → Repository → ViewModel

    Now, the data is ready to be consumed by the UI. The next step is Step-6:
    CRUD (The RecyclerView Adapter). We need to create the adapter so that the allTodos
    list inside the ViewModel can finally be displayed in our HomeFragment.
*/

