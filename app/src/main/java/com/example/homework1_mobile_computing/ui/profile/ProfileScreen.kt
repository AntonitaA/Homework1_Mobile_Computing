package com.example.homework1_mobile_computing.ui.profile

import android.content.SharedPreferences
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.homework1_mobile_computing.R
import com.example.homework1_mobile_computing.ui.main.MainContent
import com.example.homework1_mobile_computing.ui.main.reminderEntries.ReminderEntries
import com.example.homework1_mobile_computing.ui.theme.Purple700
import com.google.accompanist.insets.systemBarsPadding

@Composable
fun ProfileScreen(
    profileNavController: NavController,
    sharedPreferences: SharedPreferences,
    onBackPress: () -> Unit,
    username: String
){
//allows us to have access to the interface
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        ProfileContent(
            contentNavigationController = profileNavController,
            sharedPreferences,
            onBackPress = onBackPress,
            username
        )
    }
}

@Composable
fun ProfileContent(
    contentNavigationController: NavController,
    sharedPreferences: SharedPreferences,
    onBackPress: () -> Unit,
    username: String
){
    Scaffold(
        modifier = Modifier.padding(bottom = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .systemBarsPadding(),
        ) {
            val appBarColor = MaterialTheme.colors.surface.copy(alpha = 0.87f)

            ProfileScreenBar(
                barBackgroundColor = appBarColor,
                profileNavController = contentNavigationController,
                onBackPress = onBackPress
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(200.dp),
                tint = Purple700,
            )
            Spacer(modifier = Modifier.height(20.dp))


            Text(
                text = "Username: " + username,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 50.dp),
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Password: " + sharedPreferences.getString(username, ""),
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 50.dp),
                fontSize = 15.sp
            )
        }
    }
}


@Composable
private fun ProfileScreenBar(
    profileNavController: NavController,
    onBackPress: () -> Unit,
    barBackgroundColor : Color
){
    TopAppBar(
        title = {
            Text(
                text = "Your Profile",
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 50.dp),
                fontSize = 25.sp
            )
        },
        backgroundColor = barBackgroundColor,
        navigationIcon = {
            IconButton(onClick = { onBackPress() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
        },
        actions = {
            IconButton(onClick = { profileNavController.navigate("LoginScreen") }) {
                Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = stringResource(R.string.exit))
            }
        }

    )

}