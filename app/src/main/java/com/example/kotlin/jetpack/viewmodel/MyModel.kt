package com.example.kotlin.jetpack.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel

class MyModel : ViewModel() {

    fun doSomethings(){

    }

    /**
     * 销毁时调用
     */
    override fun onCleared() {
        super.onCleared()
        Log.e(">>>>>>>","onCleared ${System.currentTimeMillis()}")
    }
}