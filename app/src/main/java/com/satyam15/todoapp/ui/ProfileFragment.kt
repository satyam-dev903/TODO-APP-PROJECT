package com.satyam15.todoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.satyam15.todoapp.databinding.FragmentProfileBinding
import com.satyam15.todoapp.viewmodel.TodoViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TodoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe the same list of todos
        viewModel.allTodos.observe(viewLifecycleOwner) { todos ->
            val completedCount = todos.count { it.isDone }
            val pendingCount = todos.size - completedCount

            // Update the UI cards we designed earlier
            binding.tvCompletedCount.text = completedCount.toString()
            binding.tvPendingCount.text = pendingCount.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}