package com.example.homework1_mobile_computing

import android.app.Application

class ReminderAndroidApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}