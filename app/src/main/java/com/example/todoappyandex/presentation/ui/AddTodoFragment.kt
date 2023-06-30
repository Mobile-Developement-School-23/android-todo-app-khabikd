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

    private var importance: TodoItem.Importance = TodoItem.Importance.DEFAULT

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
                    importance = TodoItem.Importance.DEFAULT
                    binding.priorityText.text = menuItem.title.toString()
                    true
                }
                R.id.action_priority_low -> {
                    importance = TodoItem.Importance.LOW
                    binding.priorityText.text = menuItem.title.toString()
                    true
                }
                R.id.action_priority_high -> {
                    importance = TodoItem.Importance.HIGH
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
                        importance = importance
                    )
                    todoListViewModel.editTodoItem(updatedTodo)
                } else {
                    // Создание новой тудушки
                        val todo = TodoItem(
                            id = UUID.randomUUID().toString(),
                            text = todoText,
                            importance = importance,
                            deadline = null,
                            done = false,
                            changed_at = null,
                            last_updated_by = "12"
                        )
                    todoListViewModel.saveTodoItem(todo)
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

