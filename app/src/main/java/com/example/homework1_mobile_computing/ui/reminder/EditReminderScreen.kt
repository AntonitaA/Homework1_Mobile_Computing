package com.example.homework1_mobile_computing.ui.reminder


import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.homework1_mobile_computing.R
import com.example.homework1_mobile_computing.ui.theme.Purple200
import com.example.homework1_mobile_computing.ui.theme.Purple700
import com.google.accompanist.insets.systemBarsPadding
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homework1_mobile_computing.data.entity.Reminder

import kotlinx.coroutines.launch
import java.lang.NumberFormatException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun EditReminderScreen(
    editNavController: NavController,
    onBackPress: () -> Unit,
    selectedReminderId: String,
    //username: String,
    viewModel: ReminderViewModel = viewModel()
) {
    //allows us to have access to the interface
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        EditReminderContent(
            contentNavigationController = editNavController,
            onBackPress = onBackPress,
            selectedReminderId,
            //username,
            viewModel
        )
    }
}


@Composable
fun EditReminderContent(
    contentNavigationController: NavController,
    onBackPress: () -> Unit,
    selectedReminderId:String,
    //username: String,
    viewModel: ReminderViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        val appBarColor = MaterialTheme.colors.surface.copy(alpha = 0.87f)
        val newTitle = rememberSaveable { mutableStateOf("") }
        val newDate = rememberSaveable { mutableStateOf( "" )}
        val newTime = rememberSaveable { mutableStateOf( "" )}

        val coroutineScope = rememberCoroutineScope()

        EditReminderScreenBar(
            editNavController = contentNavigationController,
            onBackPress = onBackPress,
            barBackgroundColor = appBarColor
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Reminder Title
        OutlinedTextField(
            value = newTitle.value,
            onValueChange = {
                    data -> newTitle.value = data
            },
            label = { Text("Reminder Title") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Purple700,
                unfocusedBorderColor = Purple200
            )
        )
        Spacer(modifier = Modifier.height(10.dp))

        /*// Reminder Time
        OutlinedTextField(
            value = newTime.value,
            onValueChange = {
                    data -> newTime.value = data
            },
            label = { Text("Time") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Purple700,
                unfocusedBorderColor = Purple200
            )
        )
        Spacer(modifier = Modifier.height(10.dp))*/

        // Reminder Date
        OutlinedTextField(

            value = newDate.value,
            onValueChange = {
                    data:String -> newDate.value = data
            },
            label = { Text("Date") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Purple700,
                unfocusedBorderColor = Purple200
            )
        )
        Spacer(modifier = Modifier.height(40.dp))

        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Button(
                onClick = {
                    if (checkDate(givenDate = newDate.value) != null &&
                        newTitle.value != "" &&
                        newDate.value != ""
                    ) {
                        coroutineScope.launch {
                            val selectedReminder: Reminder =
                                viewModel.getReminder(selectedReminderId.toLong())

                            viewModel.updateReminderElements(
                                Reminder(
                                    id = selectedReminder.id,
                                    title = newTitle.value,
                                    cordX = selectedReminder.cordX,
                                    cordY = selectedReminder.cordY,
                                    time = Date().time, /*TODO*/
                                    date = newDate.value,
                                    seen = selectedReminder.seen,
                                    creationTime = selectedReminder.creationTime,
                                    creationDate = selectedReminder.creationDate,
                                    creatorId = selectedReminder.creatorId

                                )
                            )
                        }
                    }
                },

                enabled = true,
                modifier = Modifier
                    .width(300.dp)
                    .size(50.dp),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(backgroundColor = Purple700)
            ) {
                Text(
                    text = "Update the reminder",
                    color = Color.White
                )
            }
        }
    }



}



@Composable
private fun EditReminderScreenBar(
    editNavController: NavController,
    onBackPress: () -> Unit,
    barBackgroundColor : Color
){
    TopAppBar(
        title = {
            Text(
                text = "Edit Reminder",
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 50.dp),
                fontSize = 25.sp
            )
        },
        backgroundColor = barBackgroundColor,
        navigationIcon = {
            IconButton( onClick = { onBackPress() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
        }

    )
}

private fun checkDate(givenDate: String?) : String? {
    val date: String?
    try {
        val givenFormatDate =
            SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(givenDate.toString())

        val day: Long? = givenDate?.substring(0, 2)?.toLong()
        val month: Long? = givenDate?.substring(3, 5)?.toLong()
        val year: Long? = givenDate?.substring(6, givenDate.length)?.toLong()
        println("day: " + day)
        println("month: " + month)
        println("year: " + year)
        if (day != null && month != null && year != null) {
            if (day in 0..31 &&
                month in 0..12 &&
                year in 1900..2100
            ) {
                if (givenFormatDate?.compareTo(Date())!! > 0) {
                    print("current: " + Date().toString())
                    println("givenDate: " + givenDate)
                    return givenDate
                } else {
                    return null
                }
            } else {
                return null
            }

        } else {
            return null
        }

    } catch (e1: ParseException) {
        println("failure parse")
        date = null
        return date
    } catch (e2: NumberFormatException) {
        println("failure format")
        date = null
        return date
    } catch (e3: StringIndexOutOfBoundsException) {
        println("failure out")
        date = null
        return date
    }
}

