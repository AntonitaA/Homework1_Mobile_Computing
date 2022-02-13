package com.example.homework1_mobile_computing.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class ReminderApplicationState(
    val navigationController: NavHostController
) {
    fun navigateBack(){
        navigationController.popBackStack()
    }
}

@Composable
fun rememberState(
    navigationController: NavHostController = rememberNavController()
) = remember(navigationController){
    ReminderApplicationState(navigationController)
}
