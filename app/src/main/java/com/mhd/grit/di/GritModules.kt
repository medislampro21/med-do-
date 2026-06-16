package com.mhd.grit.di

import com.mhd.grit.core.data.DataStoreImpl
import com.mhd.grit.core.data.DatastoreFactory
import com.mhd.grit.core.data.NotificationAlarmScheduler
import com.mhd.grit.core.data.backup.export.ExportImpl
import com.mhd.grit.core.data.backup.restore.RestoreImpl
import com.mhd.grit.core.domain.AlarmScheduler
import com.mhd.grit.core.domain.GritDatastore
import com.mhd.grit.core.domain.backup.ExportRepo
import com.mhd.grit.core.domain.backup.RestoreRepo
import com.mhd.grit.core.habits.domain.HabitRepo
import com.mhd.grit.core.tasks.domain.TaskRepo
import com.mhd.grit.habits.data.database.HabitDatabase
import com.mhd.grit.habits.data.database.HabitDbFactory
import com.mhd.grit.habits.data.repository.HabitRepository
import com.mhd.grit.tasks.data.database.TaskDatabase
import com.mhd.grit.tasks.data.database.TaskDbFactory
import com.mhd.grit.tasks.data.repository.TasksRepository
import com.mhd.grit.viewmodels.HabitViewModel
import com.mhd.grit.viewmodels.MainViewModel
import com.mhd.grit.viewmodels.SettingsViewModel
import com.mhd.grit.viewmodels.StateLayer
import com.mhd.grit.viewmodels.TasksViewModel
import com.mhd.grit.widgets.AllTasksWidgetRepository
import com.mhd.grit.widgets.HabitOverviewWidgetRepository
import com.mhd.grit.widgets.HabitStreakWidgetRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    // databases
    singleOf(::HabitDbFactory)
    singleOf(::TaskDbFactory)
    single {
        get<HabitDbFactory>()
            .create()
            .build()
    }
    single {
        get<TaskDbFactory>()
            .create()
            .build()
    }

    single { get<HabitDatabase>().habitDao() }
    single { get<HabitDatabase>().habitStatusDao() }
    single { get<TaskDatabase>().taskDao() }
    single { get<TaskDatabase>().categoryDao() }

    singleOf(::HabitRepository).bind<HabitRepo>()
    singleOf(::TasksRepository).bind<TaskRepo>()

    singleOf(::ExportImpl).bind<ExportRepo>()
    singleOf(::RestoreImpl).bind<RestoreRepo>()

    // Datastore
    singleOf(::DatastoreFactory)
    single { get<DatastoreFactory>().getPreferencesDataStore() }
    singleOf(::DataStoreImpl).bind<GritDatastore>()

    // scheduler
    singleOf(::NotificationAlarmScheduler).bind<AlarmScheduler>()

    // widget repositories
    factoryOf(::HabitOverviewWidgetRepository)
    factoryOf(::HabitStreakWidgetRepository)
    factoryOf(::AllTasksWidgetRepository)

    // تم حذف أي ذكر لـ RevenueCat أو BillingHandler من هنا نهائياً

    // view models
    // Koin سيقوم تلقائياً بحقن التبعات المطلوبة فقط بناءً على التعريفات الجديدة في الـ ViewModels
    singleOf(::StateLayer)
    viewModelOf(::MainViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::HabitViewModel)
    viewModelOf(::TasksViewModel)
}