package com.example.kotlin

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.kotlin.jetpack.lifecycle.ProcessObserve
import dagger.hilt.android.HiltAndroidApp

/**
 * 所有使用 Hilt 的应用都必须包含一个带有 @HiltAndroidApp 注释的 Application 类。
 */
@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        /**
         * 检测 前后台切换的回调
         */
        ProcessLifecycleOwner.get().lifecycle.addObserver(ProcessObserve(this))
    }
}