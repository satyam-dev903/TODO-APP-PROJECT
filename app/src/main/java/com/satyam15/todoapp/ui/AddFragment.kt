package com.satyam15.todoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.satyam15.todoapp.data.Todo
import com.satyam15.todoapp.databinding.FragmentAddBinding
import com.satyam15.todoapp.viewmodel.TodoViewModel

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    // Link to the same ViewModel shared by the activity/fragments
    private val viewModel: TodoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {
            saveTodo()
        }
    }

    private fun saveTodo() {
        val title = binding.etTodoTitle.text.toString().trim()

        // Hardcoding "High" for now to keep it simple,
        // but you could get this from a Spinner/RadioGroup
        val priority = "High"

        // 1. Validation Logic
        if (title.isNotEmpty()) {
            // 2. Create the Data Object
            val todo = Todo(
                title = title,
                isDone = false,
                priority = priority
            )

            // 3. Send to ViewModel
            viewModel.insertTodo(todo)

            // 4. Feedback to user
            Toast.makeText(requireContext(), "Task Saved!", Toast.LENGTH_SHORT).show()

            // 5. Navigate back to Home Screen
            findNavController().popBackStack()
        } else {
            binding.etTodoTitle.error = "Please enter a title"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}