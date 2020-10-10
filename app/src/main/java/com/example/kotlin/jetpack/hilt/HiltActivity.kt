package com.example.kotlin.jetpack.hilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kotlin.App
import com.example.kotlin.R
import com.example.kotlin.module.Article
import com.example.kotlin.module.HiltData
import dagger.hilt.android.AndroidEntryPoint
import java.util.function.LongFunction
import javax.inject.Inject

/**
 * 依赖项注入 (DI) 是一种广泛用于编程的技术，非常适用于 Android 开发。
 *  Hilt 在热门 DI 库 Dagger 的基础上构建而成
 *
 *  Hilt 目前支持以下 Android 类：
 *
 *  Application（通过使用 @HiltAndroidApp）
 *  Activity
 *  Fragment
 *  View
 *  Service
 *  BroadcastReceiver
 *
 *  如果您使用 @AndroidEntryPoint 为某个 Android 类添加注释，则还必须为依赖于该类的 Android 类添加注释。
 *  例如，如果您为某个 Fragment 添加注释，则还必须为使用该 Fragment 的所有 Activity 添加注释。
 *
 *  Hilt 仅支持扩展 ComponentActivity 的 Activity，如 AppCompatActivity。
 *  Hilt 仅支持扩展 androidx.Fragment 的 Fragment。
 *  Hilt 不支持保留的 Fragment。
 */
@AndroidEntryPoint //创建一个依赖容器，该容器遵循Android类的生命周期
class HiltActivity : AppCompatActivity() {

    @Inject
    lateinit var hilt: HiltData  //由 Hilt 注入的字段不能为私有字段。

    @Inject
    lateinit var dataSource: DataSource

    @Inject
    lateinit var article:Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hilt)
        Log.e(">>>>>>>>", hilt.toString())

        dataSource.test()

        Log.e(">>>>>>>>", "hash ${article.hashCode()}")

    }
}