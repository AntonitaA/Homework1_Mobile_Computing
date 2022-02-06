package com.example.homework1_mobile_computing

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.homework1_mobile_computing.ui.ReminderApplication
import com.example.homework1_mobile_computing.ui.theme.Homework1_Mobile_ComputingTheme


class MainActivity : ComponentActivity() {
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Homework1_Mobile_ComputingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ReminderApplication(sharedPreferences)
                }
            }
        }

        sharedPreferences = getSharedPreferences(
            getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("antonita", "arvanitaki")
        editor.commit()

    }
}


/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Homework1_Mobile_ComputingTheme {
        Greeting("Android")
    }
}*/
