package com.example.homework1_mobile_computing.ui

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.homework1_mobile_computing.ui.ReminderApplicationState
import com.example.homework1_mobile_computing.ui.rememberState
import com.example.homework1_mobile_computing.ui.login.LoginScreen
import com.example.homework1_mobile_computing.ui.main.AllRemindersScreen
import com.example.homework1_mobile_computing.ui.main.MainScreen
import com.example.homework1_mobile_computing.ui.maps.ReminderLocationMap
import com.example.homework1_mobile_computing.ui.profile.ProfileScreen
import com.example.homework1_mobile_computing.ui.reminder.EditReminderScreen
import com.example.homework1_mobile_computing.ui.reminder.NewReminderScreen
import com.example.homework1_mobile_computing.ui.signup.SignupScreen

@Composable
fun ReminderApplication(
    sharedPreferences: SharedPreferences,
    state: ReminderApplicationState = rememberState(),

    ){
    NavHost(
        navController = state.navigationController,
        startDestination = "LoginScreen"
    ) {
        composable(route = "LoginScreen") {
            LoginScreen(loginNavController = state.navigationController, sharedPreferences)
        }
        composable(route = "MainScreen/{username}"){ /**/
                entry -> MainScreen(
            mainNavigationController = state.navigationController,
            entry.arguments?.getString("username")?:"")
        }
        composable(route = "NewReminderScreen/{username}") { /**/
                entry -> NewReminderScreen(
            reminderNavController = state.navigationController,
            onBackPress = { state.navigateBack() },
            entry.arguments?.getString("username")?:""
        )
        }

        composable(route = "map/{buttonPressed}") {
                entry -> ReminderLocationMap(
            mapNavController = state.navigationController,
            entry.arguments?.getString("buttonPressed")?:""
        )
        }

        composable(route = "ProfileScreen/{username}"){ /**/
                entry -> ProfileScreen(
            profileNavController = state.navigationController,
            sharedPreferences,
            onBackPress = { state.navigateBack() },
            entry.arguments?.getString("username")?:""
        )
        }
        composable(route = "SignUpScreen"){
            SignupScreen(
                signNavController = state.navigationController,
                sharedPreferences,
                onBackPress = { state.navigateBack() }
            )
        }

        composable(route = "EditReminderScreen/{selectedReminderId}"){
                entry -> EditReminderScreen(
            editNavController = state.navigationController,
            onBackPress = { state.navigateBack() },
            entry.arguments?.getString("selectedReminderId")?:""
        )
        }

        composable(route = "AllRemindersScreen"){
            AllRemindersScreen(
                allNavigationController = state.navigationController
            )
        }

    }
}