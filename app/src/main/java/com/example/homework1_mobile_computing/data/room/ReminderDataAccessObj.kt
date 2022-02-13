package com.example.homework1_mobile_computing.data.room

import androidx.room.*
import com.example.homework1_mobile_computing.data.entity.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ReminderDataAccessObj {

    @Query(value = "SELECT * FROM reminders WHERE reminder_id = :reminderId")
    abstract suspend fun reminderById(reminderId: Long): Reminder

    @Query("SELECT * FROM reminders LIMIT 20")
    abstract fun remindersList(): Flow<List<Reminder>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: Reminder): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: Reminder)

    @Delete
    abstract suspend fun delete(entity: Reminder): Int
}