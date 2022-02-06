package com.example.homework1_mobile_computing.ui.reminder

import android.content.SharedPreferences
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.homework1_mobile_computing.R
import com.example.homework1_mobile_computing.ui.theme.Purple200
import com.example.homework1_mobile_computing.ui.theme.Purple700
import com.google.accompanist.insets.systemBarsPadding


@Composable
fun NewReminderScreen(
    reminderNavController: NavController,
    onBackPress: () -> Unit,
    username: String
) {
    //allows us to have access to the interface
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        NewReminderContent(
            contentNavigationController = reminderNavController,
            onBackPress = onBackPress,
            username
        )
    }
}


@Composable
fun NewReminderContent(
    contentNavigationController: NavController,
    onBackPress: () -> Unit,
    username: String
) {
    //allows us to have access to the interface
    Surface(
        modifier = Modifier.padding(bottom = 24.dp)
    ) {
        val title = rememberSaveable { mutableStateOf("") }
        val date = rememberSaveable { mutableStateOf("") }
        val time = rememberSaveable { mutableStateOf("") }
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
                    keyboardType = KeyboardType.Text
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
                        data -> date.value = data
                },
                label = { Text("Date") },
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
            Spacer(modifier = Modifier.height(40.dp))

            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                Button(
                    onClick = { contentNavigationController.navigate("MainScreen/${username}") },
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

