package com.example.homework1_mobile_computing.util

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.Worker
import androidx.work.WorkerParameters

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class NotificationWorker(
    context: Context,
    userParameters: WorkerParameters
    ) : Worker(context, userParameters) {

    override fun doWork(): Result {

        return Result.success()


        /*return try {

            *//*for (i in 0..10){
                Log.i("NotificationWorker", "Counted $i")
            }*//*
            Result.success()
        } catch (e: Exception){
           Result.failure()
        }*/
    }
}
