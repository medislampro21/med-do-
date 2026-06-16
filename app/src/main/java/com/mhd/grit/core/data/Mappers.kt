package com.mhd.grit.core.data

import com.mhd.grit.core.habits.domain.Habit
import com.mhd.grit.core.habits.domain.HabitStatus
import com.mhd.grit.core.tasks.domain.Category
import com.mhd.grit.core.tasks.domain.Task
import com.mhd.grit.habits.data.database.HabitEntity
import com.mhd.grit.habits.data.database.HabitStatusEntity
import com.mhd.grit.tasks.data.database.CategoryEntity
import com.mhd.grit.tasks.data.database.TaskEntity

fun HabitEntity.toHabit(): Habit {
    return Habit(
        id = id,
        title = title,
        description = description,
        time = time,
        days = days,
        index = index,
        reminder = reminder
    )
}

fun HabitStatusEntity.toHabitStatus(): HabitStatus {
    return HabitStatus(
        id = id,
        habitId = habitId,
        date = date
    )
}

fun Habit.toHabitEntity(): HabitEntity {
    return HabitEntity(
        id = id,
        title = title,
        description = description,
        time = time,
        index = index,
        days = days,
        reminder = reminder
    )
}

fun HabitStatus.toHabitStatusEntity(): HabitStatusEntity {
    return HabitStatusEntity(
        id = id,
        habitId = habitId,
        date = date
    )
}

fun Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        categoryId = categoryId,
        title = title,
        index = index,
        status = status,
        reminder = reminder
    )
}

fun TaskEntity.toTask(): Task {
    return Task(
        id = id,
        categoryId = categoryId,
        title = title,
        index = index,
        status = status,
        reminder = reminder
    )
}

fun CategoryEntity.toCategory(): Category {
    return Category(
        id = id,
        name = name,
        index = index,
        color = color
    )
}

fun Category.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name,
        color = color,
        index = index
    )
}