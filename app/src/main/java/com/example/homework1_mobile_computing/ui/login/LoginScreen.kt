package com.example.homework1_mobile_computing.ui.login

import android.R
import android.app.Activity
import android.app.Instrumentation
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.navigation.NavController
import com.example.homework1_mobile_computing.ui.MainActivity
import com.example.homework1_mobile_computing.ui.theme.Purple200
import com.example.homework1_mobile_computing.ui.theme.Purple700
import com.google.accompanist.insets.systemBarsPadding
import java.util.*


@Composable
fun LoginScreen(
    loginNavController: NavController,
    sharedPreferences: SharedPreferences,
) {
    //allows us to have access to the interface
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Purple200
    ) {
        val username = rememberSaveable { mutableStateOf("") }
        val password = rememberSaveable { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // icon at the login, it could also be a picture
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(200.dp),
                tint = Color.White
            )

            // space between icon and textbox of username
            Spacer(modifier = Modifier.height(10.dp))

            // TextBox for the username
            OutlinedTextField(
                value = username.value,

                onValueChange = { data ->
                    username.value = data
                },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                isError = username.value != "" && !sharedPreferencesData(
                    sharedPreferences,
                    username,
                    password
                ),
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Purple700,
                    unfocusedBorderColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(10.dp))

            // TextBox for the password
            OutlinedTextField(
                value = password.value,
                onValueChange = { data ->
                    password.value = data
                },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                isError = username.value != "" && !sharedPreferencesData(
                    sharedPreferences,
                    username,
                    password
                ),
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Purple700,
                    unfocusedBorderColor = Color.White
                ),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    if (sharedPreferencesData(sharedPreferences, username, password))
                        loginNavController.navigate("MainScreen/${username.value}")  /**/
                },
                enabled = true,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(backgroundColor = Purple700)
            ) {
                Text(
                    text = "Login",
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    loginNavController.navigate("SignUpScreen")
                },
                enabled = true,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(backgroundColor = Purple700)
            ) {
                Text(
                    text = "Sign up",
                    color = Color.White
                )
            }


        }


// Speech to Text
/*
        Text(
            text = "df"*//*MainActivity().talk*//*,
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        val context = LocalContext.current
        Button(
            onClick = {
                //MainActivity().askSpeechInput()
            },
            modifier = Modifier.clip(RoundedCornerShape(10.dp))
        ) {
            Text(
                text = "Talk",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            )
        }*/


    }
}




fun sharedPreferencesData(
    sharedPreferences: SharedPreferences,
    username: MutableState<String>,
    password: MutableState<String>
): Boolean {

    val givenUsername: String = username.value
    val givenPassword: String = password.value
    val storedPassword: String?

    return if (sharedPreferences.contains(givenUsername)) {
        storedPassword = sharedPreferences.getString(givenUsername, "")
        storedPassword == givenPassword
    }
    else {
        false
    }
}





