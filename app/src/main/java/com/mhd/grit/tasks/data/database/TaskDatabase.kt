package com.mhd.grit.tasks.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mhd.grit.core.data.Converters

@Database(
    entities = [TaskEntity::class, CategoryEntity::class],
    version = 1,
    exportSchema = true,


)
@TypeConverters(Converters::class)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun taskDao(): TasksDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        const val DB_NAME = "task_database"
        const val SCHEMA_VERSION =  5
    }
}