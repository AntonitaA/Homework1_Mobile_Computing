package com.example.homework1_mobile_computing.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.homework1_mobile_computing.data.entity.Reminder

@Database(
    entities = [Reminder::class],
    version = 5,
    exportSchema = false
)
abstract class ReminderAppDatabase : RoomDatabase(){
    abstract fun reminderDao(): ReminderDataAccessObj

}