package com.example.homework1_mobile_computing.ui.reminder


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.example.homework1_mobile_computing.Graph
import com.example.homework1_mobile_computing.R
import com.example.homework1_mobile_computing.data.entity.Reminder
import com.example.homework1_mobile_computing.data.repository.ReminderRepository
import com.example.homework1_mobile_computing.ui.main.reminderEntries.toDateString
import com.example.homework1_mobile_computing.ui.main.reminderEntries.toTimeString
import com.example.homework1_mobile_computing.util.NotificationWorker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit


class ReminderViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository
): ViewModel() {
    private val _state = MutableStateFlow(ReminderViewState())

    val state: StateFlow<ReminderViewState>
        get() = _state

    suspend fun saveReminder(reminder: Reminder): Long{
        newReminderCreatedNotification(reminder)
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

    init {
        createNotificationChannel(context = Graph.appContext)
        viewModelScope.launch {
            reminderRepository.reminders().collect { list ->
                _state.value = ReminderViewState(
                    reminders = list
                )

                val listOfReminders :List<Reminder> = state.value.reminders

                //println(listOfReminders.count())
                for (element in listOfReminders){
                    println("title " + element.title)
                    if (element.title != "Train to Helsinki" && element.title != "Meditation"){
                        println("mpike")
                        setOneTimeNotification(element)
                    } else if (element.title == "Train to Helsinki"){
                        setMultipleNotifications1(element)
                        setMultipleNotifications2(element)
                        setOneTimeNotification(element)
                    }
                }

            }
        }
    }
}

private fun setOneTimeNotification(reminder : Reminder){

    val workManager = WorkManager.getInstance(Graph.appContext)
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val differenceTime = reminder.dateAndTime - Date().time

    val notificationWorker = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInitialDelay(differenceTime, TimeUnit.MILLISECONDS)
        .setConstraints(constraints)
        .build()
    workManager.enqueue(notificationWorker)

    workManager.getWorkInfoByIdLiveData(notificationWorker.id)
        .observeForever{ workInfo ->
            if (workInfo.state == WorkInfo.State.SUCCEEDED && differenceTime >= 0){
                createSuccessNotification(reminder)
            }

        }
}

private fun setMultipleNotifications1(reminder : Reminder){

    val workManager = WorkManager.getInstance(Graph.appContext)
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val differenceTime = (reminder.dateAndTime - Date().time) - 1800000

    val notificationWorker = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInitialDelay(differenceTime, TimeUnit.MILLISECONDS)
        .setConstraints(constraints)
        .build()
    workManager.enqueue(notificationWorker)


    workManager.getWorkInfoByIdLiveData(notificationWorker.id)
        .observeForever{ workInfo ->
            if (workInfo.state == WorkInfo.State.SUCCEEDED && differenceTime >= 0){
                createMultipleNotifications(reminder, differenceTime)
            }
        }
}

private fun setMultipleNotifications2(reminder : Reminder){

    val workManager = WorkManager.getInstance(Graph.appContext)
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val differenceTime = (reminder.dateAndTime - Date().time) - 900000

    val notificationWorker = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInitialDelay(differenceTime, TimeUnit.MILLISECONDS)
        .setConstraints(constraints)
        .build()
    workManager.enqueue(notificationWorker)

    workManager.getWorkInfoByIdLiveData(notificationWorker.id)
        .observeForever{ workInfo ->
            if (workInfo.state == WorkInfo.State.SUCCEEDED && differenceTime >= 0){
                createMultipleNotifications(reminder, differenceTime)
            }
        }
}


private fun createSuccessNotification(reminder: Reminder){
    val notificationId = 1    // each notification id should be different for each notification
    val builder = NotificationCompat.Builder(Graph.appContext, "CHANNEL_ID")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle("REMINDER: " + reminder.title)
        .setContentText(
            reminder.title + " is coming up! Expand for more information"
        )
        .setStyle(NotificationCompat.BigTextStyle().bigText(
             reminder.title + " is coming up! " +
                 "\n Details " +
                "\n Title: " + reminder.title +
                "\n Date: " + reminder.dateAndTime.toDateString() +
                "\n Time: " + reminder.dateAndTime.toTimeString()))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(Graph.appContext)) {
            notify(notificationId, builder.build())
        }
}

private fun createMultipleNotifications(reminder: Reminder, differenceTime: Long){
    val notificationId = 2    // each notification id should be different for each notification
    val builder = NotificationCompat.Builder(Graph.appContext, "CHANNEL_ID")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle("REMINDER: " + reminder.title)
        .setContentText(
            reminder.title + " is coming up in " + (reminder.dateAndTime - Date().time)/60000 + " minutes!"
        )
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    with(NotificationManagerCompat.from(Graph.appContext)) {
        notify(notificationId, builder.build())
    }
}


private fun newReminderCreatedNotification(reminder: Reminder) {
    val notificationId = 1
    val builder = NotificationCompat.Builder(Graph.appContext, "CHANNEL_ID")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle("You created a new reminder")
        //.setContentText("You have ${reminder.title} on ${reminder.date} at ${reminder.time.toTimeString()}")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
    with(NotificationManagerCompat.from(Graph.appContext)) {
        notify(notificationId, builder.build())
    }
}


private fun createNotificationChannel(context: Context){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        val name = "NotificationChannelName"
        val descriptionText = "NotificationChannelDescriptionText"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }
}

/*
private fun setRecurringNotification(reminder : Reminder){

    val workManager = WorkManager.getInstance(Graph.appContext)
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val differenceTime = reminder.dateAndTime - Date().time

    val notificationWorker = PeriodicWorkRequestBuilder<NotificationWorker>(20, TimeUnit.MINUTES)
        .setInitialDelay(differenceTime, TimeUnit.MILLISECONDS)
        .setConstraints(constraints)
        .build()
    println(notificationWorker.id)
    workManager.enqueue(notificationWorker)

    workManager.getWorkInfoByIdLiveData(notificationWorker.id)
        .observeForever{ workInfo ->
            if (workInfo.state == WorkInfo.State.SUCCEEDED && differenceTime >= 0){
                createSuccessNotification(reminder)
            }

        }
}*/



data class ReminderViewState(
    val reminders: List<Reminder> = emptyList()
)