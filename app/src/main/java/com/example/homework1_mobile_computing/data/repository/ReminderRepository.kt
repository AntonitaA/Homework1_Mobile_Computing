package com.example.homework1_mobile_computing.data.repository


import com.example.homework1_mobile_computing.data.entity.Reminder
import com.example.homework1_mobile_computing.data.room.ReminderDataAccessObj
import kotlinx.coroutines.flow.Flow

class ReminderRepository(
    private val reminderDao: ReminderDataAccessObj
) {
    fun reminders(): Flow<List<Reminder>> = reminderDao.remindersList()



    suspend fun addReminder(reminder: Reminder) = reminderDao.insert(reminder)

    suspend fun updateReminder(reminder: Reminder) = reminderDao.update(reminder)

    suspend fun getReminderWithId(reminderId: Long) = reminderDao.reminderById(reminderId)

    suspend fun deleteReminder(reminder: Reminder) = reminderDao.delete(reminder)



}