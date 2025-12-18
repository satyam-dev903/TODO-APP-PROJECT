package com.satyam15.todoapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.satyam15.todoapp.data.Todo
import com.satyam15.todoapp.databinding.ItemTodoBinding

/**
 * We extend ListAdapter instead of the standard RecyclerView.Adapter.
 * Why? ListAdapter uses DiffUtil automatically, which is better for performance.
 */
class TodoAdapter(
    private val onCheckedChange: (Todo, Boolean) -> Unit,
    private val onDeleteClick: (Todo) -> Unit
) : ListAdapter<Todo, TodoAdapter.TodoViewHolder>(DiffCallback()) {

    // The ViewHolder holds the binding for a single item row
    inner class TodoViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo) {
            binding.apply {
                tvTodoTitle.text = todo.title
                tvTodoPriority.text = todo.priority
                cbDone.isChecked = todo.isDone

                // Handle checkbox click
                cbDone.setOnCheckedChangeListener { _, isChecked ->
                    onCheckedChange(todo, isChecked)
                }

                // Handle long press to delete (Industry standard for simple apps)
                root.setOnLongClickListener {
                    onDeleteClick(todo)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // DiffUtil calculates the difference between two lists efficiently
    class DiffCallback : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }
}