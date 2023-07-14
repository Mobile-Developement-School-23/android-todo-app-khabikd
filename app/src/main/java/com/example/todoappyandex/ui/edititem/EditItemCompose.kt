package com.example.todoappyandex.ui.edititem

import android.app.DatePickerDialog
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoappyandex.R
import com.example.todoappyandex.ui.theme.TodoAppYandexTheme
import com.example.todoappyandex.util.importanceFromString
import com.example.todoappyandex.util.timestampToFormattedDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditItemScreen(editItemViewModel: EditItemViewModel, onNavigate: () -> Unit) {
    val importanceMenu = listOf("DEFAULT", "LOW", "HIGH")
    val priorityText = editItemViewModel.todoItem.collectAsState().value.importance.name
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val roundedCornerRadius = 12.dp

    ModalBottomSheetLayout(
        sheetContent = {
            Column(modifier = Modifier.fillMaxWidth()) {
                importanceMenu.forEach { importance ->
                    Text(
                        text = importance,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                editItemViewModel.updatePriority(importanceFromString(importance))
                                coroutineScope.launch {
                                    sheetState.hide()
                                }
                            }
                            .padding(24.dp)
                    )
                }
            }
        },
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = roundedCornerRadius, topEnd = roundedCornerRadius),
        scrimColor = Color(0xFF000000).copy(alpha = 0.5f),
        content = {
            Column {
                TodoDescription(editItemViewModel = editItemViewModel)
                PriorityLayout(sheetState = sheetState, coroutineScope = coroutineScope, priorityText = priorityText)
                DividerLine()
                DeadlineLayout(editItemViewModel = editItemViewModel)
                DividerLine()
                ButtonsLayout(editItemViewModel = editItemViewModel, onNavigate)
            }
        }
    )
}

@Composable
fun TodoDescription(editItemViewModel: EditItemViewModel) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp), elevation = 16.dp, shape = RoundedCornerShape(8.dp)) {
        TextField(
            value = editItemViewModel.todoItem.collectAsState().value.text,
            onValueChange = { editItemViewModel.updateDescription(it) },
            textStyle = MaterialTheme.typography.body1,
            placeholder = { Text(text = "Что надо сделать...") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PriorityLayout(sheetState: ModalBottomSheetState, coroutineScope: CoroutineScope, priorityText: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                coroutineScope.launch {
                    sheetState.show()
                }
            }
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Важность",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = priorityText,
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.body2,
            )
        }
    }
}

@Composable
fun DeadlineLayout(editItemViewModel: EditItemViewModel) {
    var isDatePickerVisible by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Date?>(null) }
    val hasDeadline = editItemViewModel.todoItem.collectAsState().value.deadline != null

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Сделать до",
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h6,
            )
            if (hasDeadline) {
                Text(
                    text = editItemViewModel.todoItem.collectAsState().value.deadline.timestampToFormattedDate(),
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.secondary
                )
            }
        }

        Switch(
            checked = hasDeadline,
            onCheckedChange = { isChecked ->
                if (isChecked) {
                    isDatePickerVisible = true
                } else {
                    selectedDate = null
                    editItemViewModel.updateDeadline(null)
                }
            }
        )
    }

    if (isDatePickerVisible) {
        DatePicker(
            selectedDate = selectedDate,
            onDateSelected = { date ->
                selectedDate = date
                editItemViewModel.updateDeadline(date?.time)
                isDatePickerVisible = false
            }
        )
    }
}


@Composable
fun DatePicker(selectedDate: Date?, onDateSelected: (Date?) -> Unit) {
    val mContext = LocalContext.current
    val calendar = Calendar.getInstance()
    if (selectedDate != null) {
        calendar.time = selectedDate
    }
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    DatePickerDialog(
        mContext,
        { _, selectedYear, selectedMonth, selectedDay ->
            calendar.set(selectedYear, selectedMonth, selectedDay)
            onDateSelected(calendar.time)
        },
        year,
        month,
        day
    ).show()
}

@Composable
fun ButtonsLayout(editItemViewModel: EditItemViewModel, onNavigate: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Card(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .clickable {
                        editItemViewModel.removeTodoItem()
                        onNavigate()
                    }
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.delete_icon),
                        contentDescription = "Delete",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Удалить",
                        color = Color.Red,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Card(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .clickable {
                        editItemViewModel.saveTodoItem()
                        onNavigate()
                    }
            ) {
                Text(
                    text = "Сохранить",
                    color = MaterialTheme.colors.secondary,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun DividerLine() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        thickness = 2.dp
    )
}

