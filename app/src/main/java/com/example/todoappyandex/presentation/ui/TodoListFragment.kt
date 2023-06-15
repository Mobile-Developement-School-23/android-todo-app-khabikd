package com.example.todoappyandex.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoappyandex.R
import com.example.todoappyandex.data.repository.TodoItemsRepository
import com.example.todoappyandex.databinding.FragmentTodoListBinding
import com.example.todoappyandex.presentation.adapter.TodoListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class TodoListFragment : Fragment() {

    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var addBtn: FloatingActionButton
    private lateinit var completedTodoCountTextView: TextView
    private lateinit var adapter: TodoListAdapter
    private val todoListViewModel: TodoListViewModel by activityViewModels {
        TodoListViewModelFactory((TodoItemsRepository()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView
        addBtn = binding.addBtn
        completedTodoCountTextView = binding.todoDoneCount
        adapter = TodoListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewLifecycleOwner.lifecycleScope.launch {
            todoListViewModel.todoList.collect() { todoItems ->
                todoItems.let { adapter.submitList(it) }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            todoListViewModel.completedTodoCount.collect() {count ->
                completedTodoCountTextView.text = getString(R.string.done_count, count)
            }
        }

        addBtn.setOnClickListener {
            findNavController().navigate(R.id.addTodoFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

