package com.mhd.grit.core.domain

import com.mhd.grit.core.habits.domain.Habit
import com.mhd.grit.core.tasks.domain.Task

interface AlarmScheduler {
    fun schedule(habit: Habit)
    fun schedule(task: Task)

    fun cancel(habit: Habit)
    fun cancel(task: Task)

    fun cancelAll()
}