package com.example.homework1_mobile_computing.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.example.homework1_mobile_computing.R
import com.example.homework1_mobile_computing.ui.theme.Homework1_Mobile_ComputingTheme
import java.util.*


class MainActivity : ComponentActivity() {
    lateinit var sharedPreferences: SharedPreferences
    //var talk: String = "Speech text should come here"

    //private var talk by mutableStateOf("Speech text should come here")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        /*val alertDialog = android.app.AlertDialog.Builder(this).create()
        alertDialog.setTitle("Title")
        alertDialog.setMessage("This is VERY important. Do you agree?")
        alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "Yes!") { dialog, which ->
            Toast.makeText(applicationContext, "You said yes!", Toast.LENGTH_SHORT).show()
        }
        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEGATIVE,"No") { _, _ ->
            Toast.makeText(applicationContext, "Nooooo", Toast.LENGTH_SHORT).show()
        }
        alertDialog.show()*/



        setContent {
            Homework1_Mobile_ComputingTheme {

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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 102 && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            talk = result?.get(0).toString()
        }
    }


    public fun askSpeechInput(context:Context) {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            print("no")
            Toast.makeText(context, "Speech not Available", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Talk Something")

            startActivityForResult(intent, 102)

        }
    }*//*
}

*/

