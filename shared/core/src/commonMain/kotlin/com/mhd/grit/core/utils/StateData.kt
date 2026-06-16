package com.mhd.grit.core.utils

import com.mhd.grit.core.habits.domain.HabitWithAnalytics
import com.mhd.grit.core.habits.domain.OverallAnalytics
import com.mhd.grit.core.tasks.domain.Category
import com.mhd.grit.core.tasks.domain.Task
import kotlinx.datetime.DayOfWeek
import kotlinx.serialization.Serializable

// model for communicating between server and client
@Serializable
data class StateData(
    val taskData: Map<Category, List<Task>> = emptyMap(),
    val completedTasks: List<Task> = emptyList(),
    val habitData: List<HabitWithAnalytics> = emptyList(),
    val completedHabitIds: List<Long> = emptyList(),
    val overallAnalytics: OverallAnalytics = OverallAnalytics(),
    val startingDay: DayOfWeek = DayOfWeek.MONDAY,
    val is24Hr: Boolean = false,
    val isUserSubscribed: Boolean = false,
)
