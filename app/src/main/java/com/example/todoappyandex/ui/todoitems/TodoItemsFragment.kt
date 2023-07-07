package com.example.todoappyandex.ui.todoitems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoappyandex.R
import com.example.todoappyandex.data.model.TodoItem
import com.example.todoappyandex.databinding.FragmentTodoItemsBinding
import com.example.todoappyandex.ui.TodoViewModelFactory
import com.example.todoappyandex.ui.edititem.EditItemFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TodoItemsFragment : Fragment(), TodoItemChangeCallbacks {

    private lateinit var todoAdapter: TodoItemsAdapter

    private val todoListViewModel: TodoItemsViewModel by viewModels { TodoViewModelFactory() }

    private var _binding: FragmentTodoItemsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView()
        setupCreateTaskButton()
    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                todoListViewModel.completedTasksCount.collectLatest {
                    binding.todoDoneCount.text = String.format(
                        getString(R.string.done_count),
                        it
                    )
                }
            }
        }
    }

    private fun setupRecyclerView() {
        todoAdapter = TodoItemsAdapter(this)
        binding.recyclerView.adapter = todoAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                todoListViewModel.todoItems.collectLatest {
                    todoAdapter.submitList(it)
                }
            }
        }
    }

    private fun setupCreateTaskButton() {
        binding.addBtn.setOnClickListener {
            findNavController().navigate(R.id.editItemFragment)
        }
    }

    override fun onTodoItemClicked(todoItem: TodoItem) {
        val args = bundleOf(EditItemFragment.ARG_TODO_ITEM_ID to todoItem.id)
        findNavController().navigate(R.id.action_todoItemsFragment_to_editItemFragment, args)
    }

    override fun onTodoItemCheckedChanged(todoItem: TodoItem, isChecked: Boolean, position: Int) {
        todoListViewModel.updateChecked(todoItem, isChecked)
    }
}