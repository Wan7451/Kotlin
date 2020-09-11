package com.example.kotlin.jetpack.navigation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.Navigation
import com.example.kotlin.R

class NavigationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
    }

}
