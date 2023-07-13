package com.example.todoappyandex.ui.todoitems

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoappyandex.R
import com.example.todoappyandex.data.model.Importance
import com.example.todoappyandex.data.model.TodoItem
import com.example.todoappyandex.databinding.TodoItemBinding

interface TodoItemChangeCallbacks {
    fun onTodoItemClicked(todoItem: TodoItem)
    fun onTodoItemCheckedChanged(todoItem: TodoItem, isChecked: Boolean, position: Int)
}

class TodoItemsAdapter(
    private val callbacks: TodoItemChangeCallbacks
) : ListAdapter<TodoItem, TodoItemViewHolder>(TodoItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TodoItemBinding.inflate(inflater, parent, false)
        return TodoItemViewHolder(binding, callbacks)
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        val todoItem = getItem(position)
        holder.onBind(todoItem, position)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }
}

class TodoItemViewHolder(
    private val binding: TodoItemBinding,
    private val callbacks: TodoItemChangeCallbacks // viewmodel
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(todoItem: TodoItem, position: Int){
        setTaskDescription(todoItem)
        setPriorityIcon(todoItem)
        setCheckBox(todoItem, position)
    }

    private fun setCheckBox(todoItem: TodoItem, position: Int) {
        binding.checkbox.isChecked = todoItem.isCompleted
        binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            callbacks.onTodoItemCheckedChanged(todoItem, isChecked, position)
        }
    }

    private fun setPriorityIcon(todoItem: TodoItem) {
        when (todoItem.importance) {
            Importance.LOW -> {
                binding.priorityImage.visibility = View.VISIBLE
                binding.priorityImage.setImageResource(R.drawable.priority_low)
            }
            Importance.HIGH -> {
                binding.priorityImage.visibility = View.VISIBLE
                binding.priorityImage.setImageResource(R.drawable.priority_high)
            }
            Importance.DEFAULT -> {
                binding.priorityImage.visibility = View.GONE
            }
        }
    }

    private fun setTaskDescription(todoItem: TodoItem) {
        binding.todoTxt.text = todoItem.text
        if (todoItem.isCompleted) {
            binding.todoTxt.paint.isStrikeThruText = true
            binding.todoTxt.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray))
        } else {
            binding.todoTxt.paint.isStrikeThruText = false
            binding.todoTxt.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
        }

        binding.todoTxt.setOnClickListener {
            callbacks.onTodoItemClicked(todoItem)
        }
    }
}


class TodoItemDiffCallback : DiffUtil.ItemCallback<TodoItem>() {
    override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
        return oldItem == newItem
    }
}