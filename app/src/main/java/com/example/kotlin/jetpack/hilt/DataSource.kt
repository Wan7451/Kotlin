package com.example.kotlin.jetpack.hilt

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.kotlin.module.Article
import com.example.kotlin.module.HiltData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module //模块 用于向 Hlit 添加绑定,告诉 Hlit 如何提供不同类型的实例。
@InstallIn(ApplicationComponent::class)  //Install 用来告诉 Hilt 这个模块会被安装到哪个组件上.
class DataSource {

    @Inject
    constructor()

    fun test() {
        Log.e(">>>>>>", "========!!!===========")
    }

    @Provides
    @Singleton  //全局复用同一个实例,不加每次都会创建新的对象
    fun bindArticle(): Article {
        return Article().also {
            it.apkLink="${System.currentTimeMillis()}"
        }
    }

}


fun toast(app: Context, text: String) {
    Toast.makeText(app, text, Toast.LENGTH_SHORT).show()
}