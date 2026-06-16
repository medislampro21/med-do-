package com.mhd.grit.viewmodels

import com.mhd.grit.core.habits.presentation.HabitState
import com.mhd.grit.core.presentation.settings.SettingsState
import com.mhd.grit.core.tasks.presentation.TaskState
import kotlinx.coroutines.flow.MutableStateFlow

class StateLayer {
    val tasksState = MutableStateFlow(TaskState())
    val habitsState = MutableStateFlow(HabitState())
    val settingsState = MutableStateFlow(SettingsState())
}