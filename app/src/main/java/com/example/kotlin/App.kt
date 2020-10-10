package com.example.kotlin

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.kotlin.jetpack.lifecycle.ProcessObserve
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

/**
 * 所有使用 Hilt 的应用都必须包含一个带有 @HiltAndroidApp 注释的 Application 类。
 * 触发Hilt的代码生成，包括适用于应用程序的基类，可以使用依赖注入，应用程序容器是应用程序的父容器，这意味着其他容器可以访问其提供的依赖项。
 */
@HiltAndroidApp
@Module
@InstallIn(ApplicationComponent::class)
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        /**
         * 检测 前后台切换的回调
         */
        ProcessLifecycleOwner.get().lifecycle.addObserver(ProcessObserve(this))
    }


    @Singleton
    @Provides
    fun ctx(): App {
        return this
    }
}