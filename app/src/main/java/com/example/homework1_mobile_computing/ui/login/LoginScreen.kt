package com.example.homework1_mobile_computing.ui.login

import android.content.SharedPreferences
import android.content.res.Resources
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.Settings.Global.getString
import android.provider.Settings.Secure.getString
import android.provider.Settings.System.getString
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.navigation.NavController
import com.example.homework1_mobile_computing.R
import com.example.homework1_mobile_computing.ui.theme.Purple200
import com.example.homework1_mobile_computing.ui.theme.Purple700
import com.google.accompanist.insets.systemBarsPadding


@Composable
fun LoginScreen(
    loginNavController: NavController,
    sharedPreferences: SharedPreferences,
){


    //allows us to have access to the interface
    Surface(modifier = Modifier.fillMaxSize(),
            color = Purple200) {
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

                onValueChange = {
                        data -> username.value = data
                },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                isError = username.value!="" && !sharedPreferencesData(sharedPreferences, username, password),
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Purple700,
                    unfocusedBorderColor = Color.White)
            )
            Spacer(modifier = Modifier.height(10.dp))

            // TextBox for the password
            OutlinedTextField(
                value = password.value,
                onValueChange = {
                        data -> password.value = data
                              },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                isError = username.value!="" && !sharedPreferencesData(sharedPreferences, username, password),
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Purple700,
                    unfocusedBorderColor = Color.White),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    if (sharedPreferencesData(sharedPreferences, username, password))
                        loginNavController.navigate("MainScreen/${username.value}")
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
    }
}


fun sharedPreferencesData(
    sharedPreferences: SharedPreferences,
    username: MutableState<String>,
    password: MutableState<String>
): Boolean {

    val givenUsername: String = username.value
    val givenPassword: String = password.value
    //val storedUsername: String? = sharedPreferences.getString(usernameKey, passwordKey)
    val storedPassword: String?
/*
    println("given username: " + givenUsername)
    println("given password: " + givenPassword)
    //println("stored username: " + storedUsername)
    //println("stored username: " + storedPassword)*/

    return if (sharedPreferences.contains(givenUsername)) {
        storedPassword = sharedPreferences.getString(givenUsername, "")
        /*println("stored username: " + givenUsername)
        println("stored password: " + storedPassword)*/
        storedPassword == givenPassword
    }
    else {
        //println("failure")
        false
    }
}
