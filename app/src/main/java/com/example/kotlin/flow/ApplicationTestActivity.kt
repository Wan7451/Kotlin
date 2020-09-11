package com.example.kotlin.flow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kotlin.R

class ApplicationTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_application_test)

        //todo https://xiaozhuanlan.com/topic/0367548912
        Log.e(">>>>", "application ${application.javaClass.name}")
        Log.e(">>>>", "applicationContext ${applicationContext.javaClass.name}")
        Log.e(">>>>", "baseContext ${baseContext.javaClass.name}")

    }
}