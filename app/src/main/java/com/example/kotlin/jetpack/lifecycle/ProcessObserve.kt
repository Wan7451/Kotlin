package com.example.kotlin.jetpack.lifecycle

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class ProcessObserve(var context: Context) : LifecycleObserver {

    /**
     * 前台出现时调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
       toast("onResume")
        Log.e(">>>>>>", "----onResume")
    }

    /**
     * 切后台调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        toast("onPause")
        Log.e(">>>>>>", "----onPause")
    }

    /**
     * 前台出现时调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        toast("onStart")
        Log.e(">>>>>>", "----onStart")
    }
    /**
     * 切后台调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        toast("onStop")
        Log.e(">>>>>>", "----onStop")
    }

    /**
     * 创建  只会调用1次
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        toast("onCreate")
        Log.e(">>>>>>", "----onCreate")
    }

    /**
     * 永远不会调用 不会分发该事件
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        toast("onDestroy")
        Log.e(">>>>>>", "----onDestroy")
    }


    private fun toast(txt: String) {
        //Toast.makeText(context, txt, Toast.LENGTH_SHORT).show()
    }
}