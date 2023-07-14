package com.example.todoappyandex.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoappyandex.R
import com.example.todoappyandex.databinding.FragmentEditItemBinding
import com.example.todoappyandex.databinding.FragmentSettingsFragmentBinding
import com.example.todoappyandex.ui.TodoViewModelFactory
import com.example.todoappyandex.ui.edititem.EditItemScreen
import com.example.todoappyandex.ui.edititem.EditItemViewModel
import com.example.todoappyandex.ui.theme.TodoAppYandexTheme

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsFragmentBinding.inflate(inflater, container, false)
        binding.composeView.setContent {
            TodoAppYandexTheme {
                SettingsScreen()
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}