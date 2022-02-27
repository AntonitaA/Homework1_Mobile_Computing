package com.example.homework1_mobile_computing.ui.reminder

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
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
fun NewReminderScreen(
    reminderNavController: NavController,
    onBackPress: () -> Unit,
    username: String,
    viewModel: ReminderViewModel = viewModel()
) {
    //allows us to have access to the interface
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        NewReminderContent(
            contentNavigationController = reminderNavController,
            onBackPress = onBackPress,
            username,
            viewModel
        )
    }
}


@Composable
fun NewReminderContent(
    contentNavigationController: NavController,
    onBackPress: () -> Unit,
    username: String,
    viewModel: ReminderViewModel = viewModel()
) {
    val viewState by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val title = rememberSaveable { mutableStateOf("") }
    val date = rememberSaveable { mutableStateOf( "" )}
    val time = rememberSaveable { mutableStateOf("") }

    //allows us to have access to the interface
    Surface(
        modifier = Modifier.padding(bottom = 24.dp)

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            val appBarColor = MaterialTheme.colors.surface.copy(alpha = 0.87f)

            NewReminderScreenBar(
                reminderNavController = contentNavigationController,
                onBackPress = onBackPress,
                barBackgroundColor = appBarColor
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Reminder Title
            OutlinedTextField(
                value = title.value,
                onValueChange = {
                        data -> title.value = data
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

            // Reminder Time
            OutlinedTextField(
                value = time.value,
                onValueChange = {
                        data -> time.value = data
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
            Spacer(modifier = Modifier.height(10.dp))

            // Reminder Date
            OutlinedTextField(

                value = date.value,
                onValueChange = {
                        data:String -> date.value = data
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
                        if (checkDate(givenDate = date.value) != null &&
                            title.value != "" &&
                            date.value != ""
                        ) {
                            val newDateAndTime : String = date.value + " " + time.value
                            val newDateAndTimeDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).parse(newDateAndTime)

                            coroutineScope.launch {
                                viewModel.saveReminder(
                                    Reminder(
                                        title = title.value,
                                        cordX = "coordinate X",
                                        cordY = "coordinate Y",
                                        dateAndTime = newDateAndTimeDateFormat.time,
                                        seen = false,
                                        creationTime = SimpleDateFormat(
                                            "h:mm a",
                                            Locale.getDefault()
                                        ).format(Date().time),   /*TODO*/
                                        creationDate = SimpleDateFormat(
                                            "MMMM dd, yyy",
                                            Locale.getDefault()
                                        ).format(Date().time),  /*TODO*/
                                        creatorId = "creator id"
                                    )
                                )
                            }
                            contentNavigationController.navigate("MainScreen/${username}")
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
                        text = "Create the reminder",
                        color = Color.White
                    )
                }
            }
        }

    }
}



@Composable
private fun NewReminderScreenBar(
    reminderNavController: NavController,
    onBackPress: () -> Unit,
    barBackgroundColor : Color
){
    TopAppBar(
        title = {
            Text(
                text = "New Reminder",
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
        if (day != null && month != null && year != null) {
            if (day in 0..31 &&
                month in 0..12 &&
                year in 1900..2100
            ) {
                //if (givenFormatDate?.compareTo(Date())!! >= 0) {
                    return givenDate
                //} else {
                //    return null
                //}
            } else {
                return null
            }

        } else {
            return null
        }

    } catch (e1: ParseException) {
        //println("failure parse")
        date = null
        return date
    } catch (e2: NumberFormatException) {
        //println("failure format")
        date = null
        return date
    } catch (e3: StringIndexOutOfBoundsException) {
        //println("failure out")
        date = null
        return date
    }
}


