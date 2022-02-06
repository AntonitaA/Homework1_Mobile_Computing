package com.example.homework1_mobile_computing.data.entity

import java.util.*

data class Reminder(
    val reminderId: Long,
    val reminderTitle: String,
    val reminderDate: Date?,
    val reminderTime: String
)
