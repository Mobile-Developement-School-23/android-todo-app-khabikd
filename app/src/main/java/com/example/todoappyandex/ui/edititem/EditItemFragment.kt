package com.example.todoappyandex.ui.edititem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoappyandex.R
import com.example.todoappyandex.databinding.FragmentEditItemBinding
import com.example.todoappyandex.ui.TodoViewModelFactory
import com.example.todoappyandex.ui.theme.TodoAppYandexTheme

class EditItemFragment : Fragment() {

    private val editItemViewModel: EditItemViewModel by viewModels { TodoViewModelFactory() }

    private var _binding: FragmentEditItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditItemBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            TodoAppYandexTheme {
                EditItemScreen(editItemViewModel, onNavigate = { findNavController().navigate(R.id.action_editItemFragment_to_todoItemsFragment) })
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        editItemViewModel.createOrFind(arguments?.getString(ARG_TODO_ITEM_ID))
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        const val ARG_TODO_ITEM_ID = "todoItemId"
    }
}

