package com.example.kotlin.jetpack.navigation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.Navigation
import com.example.kotlin.R

class Nav1Fragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.next).setOnClickListener {
            val build = Nav1FragmentArgs.Builder().setAge(10).build().toBundle()
            Navigation.findNavController(it).navigate(R.id.action_2Nav2Fragment,build)
        }
        sendPendingIntent()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nav1, container, false)
    }

    /**
     * 通过 通知 打开
     */
    private fun sendPendingIntent() {
        //8.0 需要channel
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val channel = NotificationChannel("ID", "NAME", NotificationManager.IMPORTANCE_HIGH)
            channel.description = "description"
            val mgr = activity!!.getSystemService(NotificationManager::class.java) as NotificationManager
            mgr.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(activity!!, "ID")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("PendingIntentTest")
            .setContentText("Hello")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(getPendingIntent())
            .setAutoCancel(true)
            .build()

        val mgr = NotificationManagerCompat.from(activity!!)
        mgr.notify(111, notification)

    }


    private fun getPendingIntent(): PendingIntent {
        val bundle = Bundle()
        bundle.putInt("age",100)
        return Navigation.findNavController(activity!!,R.id.next)
            .createDeepLink()
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.nav2Fragment)
            .setArguments(bundle)
            .createPendingIntent()
    }
}