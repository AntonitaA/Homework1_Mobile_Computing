package com.example.homework1_mobile_computing.ui.main.reminderEntries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework1_mobile_computing.Graph
import com.example.homework1_mobile_computing.data.entity.Reminder
import com.example.homework1_mobile_computing.data.repository.ReminderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.sql.Time
import java.util.*

class ReminderEntriesViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ReminderEntriesViewState())

    val state: StateFlow<ReminderEntriesViewState>
        get() = _state

    init{
        viewModelScope.launch {
            reminderRepository.reminders().collect { list ->
                _state.value = ReminderEntriesViewState(
                    reminders = list
                )
            }
        }
    }
}

data class ReminderEntriesViewState(
    val reminders: List<Reminder> = emptyList()
){

}