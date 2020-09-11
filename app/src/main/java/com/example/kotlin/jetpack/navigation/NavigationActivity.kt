package com.example.kotlin.jetpack.navigation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.kotlin.R

class NavigationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        Handler().post {
            sendPendingIntent()
        }

    }

    /**
     * 通过 通知 打开
     */
    private fun sendPendingIntent() {
        //8.0 需要channel
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val channel = NotificationChannel("ID", "NAME", NotificationManager.IMPORTANCE_HIGH)
            channel.description = "description"
            val mgr = this.getSystemService(NotificationManager::class.java) as NotificationManager
            mgr.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, "ID")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("PendingIntentTest")
            .setContentText("Hello")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(getPendingIntent())
            .setAutoCancel(true)
            .build()

        val mgr = NotificationManagerCompat.from(this)
        mgr.notify(111, notification)

    }


    private fun getPendingIntent(): PendingIntent {
        val bundle = Bundle()
        bundle.putInt("age", 100)
        return findNavController()
            .createDeepLink()
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.nav2Fragment)
            .setArguments(bundle)
            .createPendingIntent()
    }

    private fun findNavController(): NavController {
        val f = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return f.findNavController()
    }
}
