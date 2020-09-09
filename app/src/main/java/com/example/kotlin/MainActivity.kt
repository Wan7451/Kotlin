package com.example.kotlin

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //todo 运算符重载
        val test1 = Test("1")
        val test2 = Test("2")
        val test3 = test1 + test2
        Log.e(">>>>>", test3.toString())
        val test4 = test3 - test1
        Log.e(">>>>>", test4.toString())

        //todo map集合默认值
        mapOf("a" to false, "a" to false, "a" to false, "a" to false).withDefault { false }


        //todo 对外  传入的参数有问题
        try {
            require(test1 == test4) { "require  参数异常" }
        } catch (_: Throwable) {
        }

        //todo 对内  自身状态不对
        try {
            check(test1 == test4) { "check  有问题" }
        } catch (_: Throwable) {
        }

        //todo in 关键字其实是 contains 操作符的简写，任意一个类只要重写了 contains 操作符，都可以使用 in 关键字
        if ("1" in test1) {
            Log.e(">>>>>", "ininininini")
        }

        //todo 带参数的单例
        WorkSingleton.getInstance(this)
    }

    //todo 伴生对象 + by lazy   懒汉式
    companion object {
        val single by lazy { Test("SINGLE") }
    }

}

//todo 单例   饿汉式且是线程安全的
object SingleTest : Test("SINGLE") {

}


class WorkSingleton private constructor(context: Context) {
    init {
        // Init using context argument
    }

    companion object : SingletonHolder<WorkSingleton, Context>(::WorkSingleton)
}


open class SingletonHolder<out T : Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile
    private var instance: T? = null

    fun getInstance(arg: A): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}
