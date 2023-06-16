package com.example.todoappyandex.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoappyandex.R
import com.example.todoappyandex.databinding.FragmentAddTodoBinding
import com.example.todoappyandex.domain.model.TodoItem
import java.util.*

class AddTodoFragment : Fragment() {

    private var _binding: FragmentAddTodoBinding? = null
    private val binding get() = _binding!!

    private var priority: TodoItem.Priority = TodoItem.Priority.DEFAULT

    private val todoListViewModel: TodoListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: AddTodoFragmentArgs by navArgs()
        val todoItem: TodoItem? = args.todoItem

        if (todoItem != null) {
            binding.todoDescription.setText(todoItem.text)
        }

        val priorityMenu = PopupMenu(requireContext(), binding.priorityLayout)
        priorityMenu.menuInflater.inflate(R.menu.menu_priority, priorityMenu.menu)

        binding.priorityLayout.setOnClickListener {
            priorityMenu.show()
        }

        priorityMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_priority_default -> {
                    priority = TodoItem.Priority.DEFAULT
                    binding.priorityText.text = menuItem.title.toString()
                    true
                }
                R.id.action_priority_low -> {
                    priority = TodoItem.Priority.LOW
                    binding.priorityText.text = menuItem.title.toString()
                    true
                }
                R.id.action_priority_high -> {
                    priority = TodoItem.Priority.HIGH
                    binding.priorityText.text = menuItem.title.toString()
                    true
                }
                else -> false
            }
        }
        binding.save.setOnClickListener {
            if (binding.todoDescription.text.toString().trim() != "") {
                val todoText = binding.todoDescription.text.toString().trim()

                if (todoItem != null) {
                    // Редактирование существующей тудушки
                    val updatedTodo = todoItem.copy(
                        text = todoText,
                        priority = priority
                    )
                    todoListViewModel.updateTodoItem(updatedTodo)
                } else {
                    // Создание новой тудушки
                    val todoPosition = todoListViewModel.todoList.value.size
                    val todo = TodoItem(
                        id = todoPosition.toString(),
                        text = todoText,
                        priority = priority,
                        deadline = null,
                        isDone = false,
                        createdDate = Date(),
                        changedDate = null
                    )
                    todoListViewModel.addTodo(todo)
                }

                findNavController().navigateUp()
            }
        }

        binding.deleteLayout.setOnClickListener {
            if (todoItem != null) {
                // Удаление тудушки из списка
                todoListViewModel.deleteTodoItem(todoItem)
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

