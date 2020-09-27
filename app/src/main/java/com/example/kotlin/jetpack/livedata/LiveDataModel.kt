package com.example.kotlin.jetpack.livedata

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LiveDataModel : ViewModel() {

    val testData = MutableLiveData<String>()
    fun test() {
        Handler(Looper.getMainLooper()).postDelayed({
            testData.value="test"
        }, 2000)
    }
}