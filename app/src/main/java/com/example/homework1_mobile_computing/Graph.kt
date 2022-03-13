package com.example.homework1_mobile_computing

import android.content.Context
import androidx.room.Room
import com.example.homework1_mobile_computing.data.repository.ReminderRepository
import com.example.homework1_mobile_computing.data.room.ReminderAppDatabase
import com.example.homework1_mobile_computing.ui.MainActivity

object Graph {

    lateinit var database: ReminderAppDatabase

// this context is available everywhere on the app
    lateinit var appContext: Context
    //
    lateinit var activity: MainActivity
    //

    val reminderRepository by lazy {
        ReminderRepository(
            reminderDao = database.reminderDao()
        )
    }

    fun provide(context: Context) {
        appContext = context
        database = Room.databaseBuilder(context, ReminderAppDatabase::class.java, "reminderData.db")
            .fallbackToDestructiveMigration()
            .build()
        //
        activity = MainActivity()
        //
    }
}