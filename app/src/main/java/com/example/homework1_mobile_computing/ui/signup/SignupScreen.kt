package com.example.homework1_mobile_computing.ui.signup

import android.content.SharedPreferences
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
//import com.example.homework1_mobile_computing.ui.login.sharedPreferencesData
import com.example.homework1_mobile_computing.ui.theme.Purple200
import com.example.homework1_mobile_computing.ui.theme.Purple500
import com.example.homework1_mobile_computing.ui.theme.Purple700
import com.google.accompanist.insets.systemBarsPadding


@Composable
fun SignupScreen(
    signNavController: NavController,
    sharedPreferences: SharedPreferences,
    onBackPress: () -> Unit
) {
    //allows us to have access to the interface
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        SignupContent(
            contentNavigationController = signNavController,
            sharedPreferences,
            onBackPress = onBackPress
        )
    }
}

@Composable
fun SignupContent(
    contentNavigationController: NavController,
    sharedPreferences: SharedPreferences,
    onBackPress: () -> Unit
) {
    Scaffold(
        modifier = Modifier.padding(bottom = 24.dp)
    ) {
        val usernameInput = rememberSaveable { mutableStateOf("") }
        val passwordInput = rememberSaveable { mutableStateOf("") }
        val firstNameInput = rememberSaveable { mutableStateOf("") }
        val lastNameInput = rememberSaveable { mutableStateOf("") }
        val emailInput = rememberSaveable { mutableStateOf("") }

        Column(
            modifier = Modifier
                .systemBarsPadding()
                .fillMaxWidth()
        ) {
            val appBarColor = MaterialTheme.colors.surface.copy(alpha = 0.87f)

            SignupScreenBar(
                signNavController = contentNavigationController,
                onBackPress = onBackPress,
                barBackgroundColor = appBarColor
            )
            Spacer(modifier = Modifier.height(10.dp))

            // first name
            OutlinedTextField(
                value = firstNameInput.value,
                onValueChange = { input -> firstNameInput.value = input },
                isError = firstNameInput.value == "",
                label = { Text("First Name") },
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

            // last name
            OutlinedTextField(
                value = lastNameInput.value,
                onValueChange = { input -> lastNameInput.value = input },
                isError = lastNameInput.value == "",
                label = { Text("Last Name") },
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

            // email
            OutlinedTextField(
                value = emailInput.value,
                onValueChange = { input -> emailInput.value = input },
                isError = emailInput.value == "",
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Purple700,
                    unfocusedBorderColor = Purple200)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // username
            OutlinedTextField(
                value = usernameInput.value,
                onValueChange = { input -> usernameInput.value = input },
                isError = usernameInput.value!="" && sharedPreferences.contains(usernameInput.value),
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Purple700,
                    unfocusedBorderColor = Purple200)
            )
            Spacer(modifier = Modifier.height(10.dp))

            // password
            OutlinedTextField(
                value = passwordInput.value,
                onValueChange = { input -> passwordInput.value = input },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Purple700,
                    unfocusedBorderColor = Purple200)
            )

            Spacer(modifier = Modifier.height(40.dp))
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                Button(
                    onClick = {
                        if (!sharedPreferences.contains(usernameInput.value) &&
                            firstNameInput.value != "" &&
                            lastNameInput.value != "" &&
                            emailInput.value != "" &&
                            usernameInput.value != "" &&
                            passwordInput.value != ""
                        ) {
                            saveDatatoSharedPreferences(sharedPreferences, usernameInput, passwordInput)
                            contentNavigationController.navigate("LoginScreen")
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
                        text = "Sign up",
                        color = Color.White
                    )
                }
            }
        }

    }
}



@Composable
private fun SignupScreenBar(
    signNavController: NavController,
    onBackPress: () -> Unit,
    barBackgroundColor : Color
){
    TopAppBar(
        title = {
            Text(
                text = "Create your account",
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 50.dp),
                fontSize = 25.sp
            )
        },
        backgroundColor = barBackgroundColor,
        navigationIcon = {
            IconButton(onClick = { onBackPress() } ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
        }
    )
}

fun saveDatatoSharedPreferences(
    sharedPreferences: SharedPreferences,
    username: MutableState<String>,
    password: MutableState<String>
){
    val newUsername: String = username.value
    val newPassword: String = password.value
    val editor: SharedPreferences.Editor = sharedPreferences.edit()

    editor.putString(newUsername, newPassword)
    editor.commit()
}
