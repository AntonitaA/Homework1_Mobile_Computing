
package com.example.homework1_mobile_computing.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.homework1_mobile_computing.R
import com.example.homework1_mobile_computing.ui.main.reminderEntries.AllReminders
import com.example.homework1_mobile_computing.ui.main.reminderEntries.ReminderEntries
import com.example.homework1_mobile_computing.ui.theme.Purple200
import com.google.accompanist.insets.systemBarsPadding


@Composable
fun AllRemindersScreen(
    allNavigationController: NavController,
){
    //allows us to have access to the interface
    Surface(modifier = Modifier.fillMaxSize()
    ) {
        AllContent(
            contentNavigationController = allNavigationController,
        )

    }
}

@Composable
fun AllContent(
    contentNavigationController: NavController,
){
    Scaffold(
        modifier = Modifier.padding(bottom = 24.dp),

        ) {
        Column(
            modifier = Modifier
                .systemBarsPadding()
                .fillMaxWidth()
        ) {
            val appBarColor = MaterialTheme.colors.surface.copy(alpha = 0.87f)

            MainScreenBar(
                barBackgroundColor = appBarColor,
                mainNavController = contentNavigationController,
            )

            AllReminders(
                modifier = Modifier.fillMaxSize(),
                contentNavigationController
            )





        }
    }
}

@Composable
private fun MainScreenBar(
    barBackgroundColor : Color,
    mainNavController: NavController,
){
    TopAppBar(
        title = {
            Text(
                text = "Reminder Application",
                color = MaterialTheme.colors.primary,
                fontSize = 25.sp,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 30.dp)
            )
        },
        backgroundColor = barBackgroundColor,
        actions = {
            IconButton(onClick = { mainNavController.navigate("LoginScreen") }) {
                Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = stringResource(R.string.exit))
            }

        }

    )

}