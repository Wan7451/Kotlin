package com.example.aidl1

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder {
        return MyBinder()
    }


    inner class MyBinder : IMyAidlInterface.Stub() {
        override fun getName(): String {
            return "name"
        }
    }
}