package com.example.homework1_mobile_computing.ui.main.reminderEntries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework1_mobile_computing.data.entity.Reminder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.sql.Time
import java.util.*

class ReminderEntriesViewModel : ViewModel(){
    private val _state = MutableStateFlow(ReminderEntriesViewState())
    val state: StateFlow<ReminderEntriesViewState>
        get() = _state
    init{
        val list = mutableListOf<Reminder>()
        for (x in 1..20){
            list.add(
                Reminder(
                    reminderId = x.toLong(),
                    reminderTitle ="$x reminder",
                    reminderDate = Date(),
                    reminderTime = "10.00"
                )
            )
        }
        viewModelScope.launch{
            _state.value = ReminderEntriesViewState(
                reminders = list
            )
        }
    }
}

data class ReminderEntriesViewState(
    val reminders: List<Reminder> = emptyList()
){

}