package com.example.todoappyandex.ui.edititem

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.todoappyandex.R
import com.example.todoappyandex.data.model.Importance
import com.example.todoappyandex.databinding.FragmentEditItemBinding
import com.example.todoappyandex.ui.TodoViewModelFactory
import com.example.todoappyandex.util.importanceToString
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar

class EditItemFragment : Fragment() {
// where FragmentComponent?
    private val editItemViewModel: EditItemViewModel by viewModels { TodoViewModelFactory() }

    private var _binding: FragmentEditItemBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViewModel()
        setupPriorityMenu()
        setupDatePicker()
        setupButtons()
        setupDescription()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupDescription() {
        binding.todoDescription.addTextChangedListener {
            editItemViewModel.updateDescription(it?.toString() ?: "")
        }
    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                editItemViewModel.todoItem.collectLatest { todoItem ->
                    binding.priorityText.text = importanceToString(todoItem.importance)
                    binding.todoDescription.setTextKeepState(todoItem.text)
                }
            }
        }

        editItemViewModel.createOrFind(arguments?.getString(ARG_TODO_ITEM_ID))
        binding.switchCalendar.isChecked = editItemViewModel.todoItem.value.deadline != null
    }

    private fun setupPriorityMenu() {
        val priorityMenu = PopupMenu(requireContext(), binding.priorityLayout)
        priorityMenu.menuInflater.inflate(R.menu.menu_importance, priorityMenu.menu)

        binding.priorityLayout.setOnClickListener {
            priorityMenu.show()
        }

        priorityMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_priority_default -> {
                    editItemViewModel.updatePriority(Importance.DEFAULT)
                    binding.priorityText.text = menuItem.title.toString()
                    true
                }
                R.id.action_priority_low -> {
                    editItemViewModel.updatePriority(Importance.LOW)
                    binding.priorityText.text = menuItem.title.toString()
                    true
                }
                R.id.action_priority_high -> {
                    editItemViewModel.updatePriority(Importance.HIGH)
                    binding.priorityText.text = menuItem.title.toString()
                    true
                }
                else -> false
            }
        }
    }

    private fun setupButtons() {
        binding.save.setOnClickListener {
            editItemViewModel.saveTodoItem()
            findNavController().navigateUp()
        }

        binding.deleteLayout.setOnClickListener {
            editItemViewModel.removeTodoItem()
            findNavController().navigateUp()
        }
    }

    private fun openDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(selectedYear, selectedMonth, selectedDay)
                val selectedDate = selectedCalendar.time
                editItemViewModel.updateDeadline(selectedDate.time)
            }, year, month, day
        )

        datePickerDialog.setOnCancelListener {
            binding.switchCalendar.isChecked = false
        }

        datePickerDialog.show()
    }

    private fun setupDatePicker() {
        binding.deadlineTitle.setOnClickListener {
            openDatePicker()
        }

        binding.switchCalendar.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                openDatePicker()
            } else {
                editItemViewModel.updateDeadline(null)
            }
        }
    }

    companion object {
        const val ARG_TODO_ITEM_ID = "todoItemId"
    }
}