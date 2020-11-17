package com.example.aidl2

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.example.aidl1.IMyAidlInterface

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val intent = Intent()
        val componentName = ComponentName("com.example.aidl1", "com.example.aidl1.MyService")
        intent.component = componentName
        bindService(intent, object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                if (service == null) return
                val asInterface = IMyAidlInterface.Stub.asInterface(service)
                val name1 = asInterface.name
                Log.e(">>>>>>", "$name1")
                Log.e(">>>>>>", "${Thread.currentThread().name}")
            }

            override fun onServiceDisconnected(name: ComponentName?) {
            }

        }, Context.BIND_AUTO_CREATE)
    }
}

