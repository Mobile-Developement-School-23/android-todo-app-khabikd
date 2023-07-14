package com.example.todoappyandex.ui.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.todoappyandex.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map


@Composable
fun SettingsScreen() {
    val themeOptions = listOf("Системная", "Светлая", "Темная")
    val selectedTheme = rememberSaveable { mutableStateOf(getSelectedThemeIndex()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Настройки",
            style = MaterialTheme.typography.h6,
            color =  MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        RadioGroup(
            options = themeOptions,
            selectedIndex = selectedTheme.value,
            onSelectedIndexChanged = { index -> selectedTheme.value = index },
        )
    }

    val onThemeSelected: (Int) -> Unit = { index ->
        when (index) {
            0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    if (selectedTheme.value != getSelectedThemeIndex()) {
        LaunchedEffect(selectedTheme.value) {
            onThemeSelected(selectedTheme.value)
        }
    }
}

private fun getSelectedThemeIndex(): Int {
    return when (AppCompatDelegate.getDefaultNightMode()) {
        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> 0
        AppCompatDelegate.MODE_NIGHT_NO -> 1
        AppCompatDelegate.MODE_NIGHT_YES -> 2
        else -> 0
    }
}

@Composable
fun RadioGroup(
    options: List<String>,
    selectedIndex: Int,
    onSelectedIndexChanged: (Int) -> Unit,
) {
    val radioButtonColor = MaterialTheme.colors.secondary

    Column {
        options.forEachIndexed { index, option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(vertical = 8.dp)
                    .selectable(
                        selected = selectedIndex == index,
                        onClick = { onSelectedIndexChanged(index) }
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedIndex == index,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = radioButtonColor,
                        unselectedColor = radioButtonColor
                    ),
                    onClick = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = option,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}