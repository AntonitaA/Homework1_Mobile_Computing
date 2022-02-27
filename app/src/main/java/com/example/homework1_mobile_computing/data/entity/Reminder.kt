package com.example.homework1_mobile_computing.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.sql.Time
import java.util.*

@Entity(
    tableName = "reminders",
    indices = [
        Index("reminder_id", unique = true)
    ],
)
data class Reminder(
    @PrimaryKey(autoGenerate = true)  @ColumnInfo(name = "reminder_id") val id:Long = 0,
    @ColumnInfo(name = "reminder_title") val title: String,
    @ColumnInfo(name = "coordinate_X") val cordX: String,
    @ColumnInfo(name = "coordinate_Y") val cordY: String,
    @ColumnInfo(name = "reminder_date") val dateAndTime: Long,
    @ColumnInfo(name = "reminder_seen") val seen: Boolean,
    @ColumnInfo(name = "reminder_creation_time") val creationTime: String,
    @ColumnInfo(name = "reminder_creation_date") val creationDate: String,
    @ColumnInfo(name = "reminder_creator_id") val creatorId: String
)
