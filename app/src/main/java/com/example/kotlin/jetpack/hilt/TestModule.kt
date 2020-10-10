package com.example.kotlin.jetpack.hilt

import com.example.kotlin.module.HiltData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

/**
 * 组件(Compenent)
 *
 * Hitl默认有以下标准组件，只需要在类上增加 @AndroidEntryPoint 即可支持以下类的注入
 *
 * Compenent                        Injector for
 *
 * ApplicationComponent             Application
 * ActivityRetainedComponent        ViewModel
 * ActivityComponent                Activity
 * FragmentComponent                Fragment
 * ViewComponent                    View
 * ViewWithFragmentComponent        View 与 @WithFragmentBindings
 * ServiceComponent                 Service
 *
 *
 * 组件(Compenent)的生命周期
 *     它限制了在创建组件和生成组件范围绑定的生命周期
 *     它指示合适可以使用成员注入的值。(例如：当@Inject 字段不为null时)
 *
 * Component                    作用范围                     Created at                  Destroyed at
 *
 * ApplicationComponent         @Singleton                  Application#onCreate()      Application#onDestroy()
 * ActivityRetainedComponent    @ActivityRetainedScope      Activity#onCreate()         Activity#onDestroy()
 * ActivityComponent            @ActivityScoped             Activity#onCreate()         Activity#onDestroy()
 * FragmentComponent            @FragmentScoped             Fragment#onAttach()         Fragment#onDestroy()
 * ViewComponent                @ViewScoped                 View#super()                View destroyed
 * ViewWithFragmentComponent    @ViewScoped                 View#super()                View destroyed
 * ServiceComponent             @ServiceScoped              Service#onCreate()          Service#onDestroy()
 *
 *
 * 默认情况下，所有的绑定都是无作用域，也就是说，每次绑定时，都会创建一个新的绑定实例；
 * 但是，Dagger 允许绑定作用域到特定组件，如上表所示，在指定组件范围内，实例都只会创建一次，并且对该绑定的所有请求都将共享同一实例。
 *
 */


@InstallIn(ApplicationComponent::class)  //Install 用来告诉 Hilt 这个模块会被安装到哪个组件上.
@Module //模块用于向 Hlit 添加绑定
object TestModule {

    @Provides  //优先级高？
    @Singleton  //单例
    fun generate(): HiltData {
        return HiltData("name", 10000)
    }

}