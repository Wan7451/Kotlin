package com.example.kotlin.jetpack.hilt

import com.example.kotlin.module.HiltData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module //模块用于向 Hlit 添加绑定
object TestModule {

    @Provides  //优先级高？
    @Singleton  //单例
    fun generate(): HiltData {
        return HiltData("name", 10000)
    }

}