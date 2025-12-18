package com.satyam15.todoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.satyam15.todoapp.databinding.FragmentHomeBinding
import com.satyam15.todoapp.viewmodel.TodoViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Get the ViewModel using the 'viewModels' delegate
    private val viewModel: TodoViewModel by viewModels()
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        // OBSERVE: This is where the magic happens.
        // Whenever the Room database changes, this block runs automatically!
        viewModel.allTodos.observe(viewLifecycleOwner) { list ->
            todoAdapter.submitList(list)
        }
    }

    private fun setupRecyclerView() {
        todoAdapter = TodoAdapter(
            onCheckedChange = { todo, isChecked ->
                viewModel.updateTodoStatus(todo.id, isChecked)
            },
            onDeleteClick = { todo ->
                viewModel.deleteTodo(todo)
                Toast.makeText(requireContext(), "Task Deleted", Toast.LENGTH_SHORT).show()
            }
        )
        binding.rvTodo.adapter = todoAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}