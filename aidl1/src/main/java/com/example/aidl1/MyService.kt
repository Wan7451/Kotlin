package com.example.aidl1

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.util.ArrayList

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder {
        return MyBinder()
    }


    inner class MyBinder : IMyAidlInterface.Stub() {
        private val users = ArrayList<Person>()

        override fun addPerson(person: Person?): MutableList<Person> {
            Log.e(">>>>>>> Server", Thread.currentThread().name)
            person?.let { users.add(it) }
            return users
        }

        override fun addPersononly(person: Person?) {
            Log.e(">>>>>>> Server", Thread.currentThread().name)
            person?.let { users.add(it) }
        }
    }
}