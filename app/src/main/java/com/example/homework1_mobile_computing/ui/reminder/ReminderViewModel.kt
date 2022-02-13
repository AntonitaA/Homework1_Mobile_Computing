package com.example.homework1_mobile_computing.ui.reminder


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework1_mobile_computing.Graph
import com.example.homework1_mobile_computing.data.entity.Reminder
import com.example.homework1_mobile_computing.data.repository.ReminderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReminderViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository
): ViewModel() {
    private val _state = MutableStateFlow(ReminderViewState())

    val state: StateFlow<ReminderViewState>
        get() = _state

    suspend fun saveReminder(reminder: Reminder): Long{
        return reminderRepository.addReminder(reminder)
    }

    suspend fun updateReminderElements(reminder: Reminder){
        return reminderRepository.updateReminder(reminder)
    }

    suspend fun getReminder(reminderId: Long): Reminder{
        return reminderRepository.getReminderWithId(reminderId)
    }

    suspend fun deleteReminder(reminder: Reminder): Int {
        return reminderRepository.deleteReminder(reminder)
    }


}

data class ReminderViewState(
    val reminders: List<Reminder> = emptyList()
)