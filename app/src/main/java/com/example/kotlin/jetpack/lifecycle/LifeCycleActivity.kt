package com.example.kotlin.jetpack.lifecycle

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.example.kotlin.R
import java.util.*

class LifeCycleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_cycle)

        val component = MyComponent()
        lifecycle.addObserver(component)

        val t = Test(this)
        t.lifecycle.addObserver(component)
        t.execute()

        lifecycle.addObserver(T2())
    }
}

class Test(var context: Context) : LifecycleOwner {
    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }

    private val mLifecycleRegistry = LifecycleRegistry(this)

    fun start() {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        context.startService(Intent(context, LifeCycleServicee::class.java))
    }

    fun stop() {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        context.stopService(Intent(context, LifeCycleServicee::class.java))
    }

    var isStart = false
    fun execute() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                if (isStart) {
                    start()
                } else {
                    stop()
                }
                isStart = !isStart
            }
        }, 0, 2000)
    }
}