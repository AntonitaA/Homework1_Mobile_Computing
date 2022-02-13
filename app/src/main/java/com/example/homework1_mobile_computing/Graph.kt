package com.example.homework1_mobile_computing

import android.content.Context
import androidx.room.Room
import com.example.homework1_mobile_computing.data.repository.ReminderRepository
import com.example.homework1_mobile_computing.data.room.ReminderAppDatabase

object Graph {

    lateinit var database: ReminderAppDatabase

    val reminderRepository by lazy {
        ReminderRepository(
            reminderDao = database.reminderDao()
        )
    }

    fun provide(context: Context) {
        database = Room.databaseBuilder(context, ReminderAppDatabase::class.java, "reminderData.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}