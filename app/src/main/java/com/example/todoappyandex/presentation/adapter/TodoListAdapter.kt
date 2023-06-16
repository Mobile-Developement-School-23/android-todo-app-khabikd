package com.example.todoappyandex.presentation.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoappyandex.R
import com.example.todoappyandex.databinding.TodoItemBinding
import com.example.todoappyandex.domain.model.TodoItem

class TodoListAdapter(private val listener: OnItemClickListener): ListAdapter<TodoItem, TodoListAdapter.TodoItemViewHolder>(TodoItemDiffCallback()) {

    interface OnItemClickListener {
        fun onItemClick(todoItem: TodoItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        val todoItem = getItem(position)
        holder.bind(todoItem)
    }

    inner class TodoItemViewHolder(private val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val todoItem = getItem(position)
                    listener.onItemClick(todoItem)
                }
            }
        }

        fun bind(todoItem: TodoItem) {
            binding.apply {
                // Установка состояния чекбокса и текста дела
                checkbox.isChecked = todoItem.isDone
                todoTxt.text = todoItem.text
                when (todoItem.priority) {
                    TodoItem.Priority.HIGH -> { priorityImage.setImageResource(R.drawable.priority_high); priorityImage.visibility = View.VISIBLE }
                    TodoItem.Priority.LOW -> { priorityImage.setImageResource(R.drawable.priority_low); priorityImage.visibility = View.VISIBLE }
                    else -> { priorityImage.visibility = View.GONE }
                }
                if (todoItem.isDone) {
                    todoTxt.paintFlags = todoTxt.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    todoTxt.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray))
                } else {
                    todoTxt.paintFlags = todoTxt.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    todoTxt.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
                }

                // Обработка нажатия на чекбокс
                binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                    todoItem.isDone = isChecked
                    if (isChecked) {
                        todoTxt.paintFlags = todoTxt.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                        todoTxt.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray))
                        priorityImage.visibility = View.GONE
                    } else {
                        todoTxt.paintFlags = todoTxt.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                        todoTxt.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
                        when (todoItem.priority) {
                            TodoItem.Priority.HIGH -> { priorityImage.setImageResource(R.drawable.priority_high); priorityImage.visibility = View.VISIBLE }
                            TodoItem.Priority.LOW -> { priorityImage.setImageResource(R.drawable.priority_low); priorityImage.visibility = View.VISIBLE }
                            else -> { priorityImage.visibility = View.GONE }
                        }
                    }
                }
            }
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