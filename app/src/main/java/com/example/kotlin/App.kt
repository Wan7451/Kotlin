package com.example.kotlin

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.kotlin.jetpack.lifecycle.ProcessObserve

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(ProcessObserve(this))
    }
}