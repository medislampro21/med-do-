package com.mhd.grit.tasks.data.repository

import com.mhd.grit.core.data.toCategory
import com.mhd.grit.core.data.toCategoryEntity
import com.mhd.grit.core.data.toTask
import com.mhd.grit.core.data.toTaskEntity
import com.mhd.grit.core.tasks.domain.Category
import com.mhd.grit.core.tasks.domain.Task
import com.mhd.grit.core.tasks.domain.TaskRepo
import com.mhd.grit.tasks.data.database.CategoryDao
import com.mhd.grit.tasks.data.database.TasksDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class TasksRepository(
    private val tasksDao: TasksDao,
    private val categoryDao: CategoryDao,
) : TaskRepo {

    private val tasksFlow = tasksDao
        .getTasksFlow()
        .map { entities -> entities.map { it.toTask() }.sortedBy { it.index } }
        .flowOn(Dispatchers.IO)

    val categoriesFlow = categoryDao
        .getCategoriesFlow()
        .map { entities -> entities.map { it.toCategory() }.sortedBy { it.index } }
        .flowOn(Dispatchers.IO)

    override fun getTasksFlow(): Flow<Map<Category, List<Task>>> {
        return tasksFlow.combine(categoriesFlow) { tasks, categories ->
            categories.associateWith { category ->
                tasks.filter { it.categoryId == category.id }
            }
        }.flowOn(Dispatchers.Default)
    }

    override fun getCompletedTasksFlow(): Flow<List<Task>> {
        return tasksFlow
            .map { tasks -> tasks.filter { it.status } }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun getTasks(): List<Task> {
        return tasksDao.getTasks().map { it.toTask() }
    }

    override suspend fun getTaskById(id: Long): Task? {
        return tasksDao.getTaskById(id)?.toTask()
    }

    override suspend fun getCategories(): List<Category> {
        return categoryDao.getCategories().map { it.toCategory() }
    }

    override suspend fun updateTaskIndexById(id: Long, index: Int) {
        tasksDao.updateTaskIndexById(id, index)
    }

    override suspend fun upsertTask(task: Task) {
        tasksDao.upsertTask(task.toTaskEntity())
    }

    override suspend fun deleteTask(task: Task) {
        tasksDao.deleteTask(task.toTaskEntity())
    }

    override suspend fun deleteAllTasks() {
        tasksDao.deleteAllTasks()
    }

    override suspend fun upsertCategory(category: Category) {
        categoryDao.upsertCategory(category.toCategoryEntity())
    }

    override suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category.toCategoryEntity())
    }

    override suspend fun deleteAllCategories() {
        categoryDao.deleteAllCategories()
    }
}