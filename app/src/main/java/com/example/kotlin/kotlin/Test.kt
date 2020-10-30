package com.example.kotlin.kotlin

import android.util.Log

open class Test(var txt: String) {


    operator fun plus(o: Test): Test {
        return Test(txt + o.txt)
    }

    operator fun minus(o: Test): Test {
        return Test(txt.replace(o.txt, ""))
    }

    operator fun contains(o: String): Boolean {
        return txt.contains(o)
    }

    override fun toString(): String {
        return txt
    }


    fun test() {
        val a = Test("ab")
        val p1 = Test::contains
        val p2 = a::contains
        val r1 = p1.invoke(a, "b")
        val r2 = p2.invoke("b")
        Log.e(">>>>>>", "$r1")
        Log.e(">>>>>>", "$r2")
        p1(a, "c")
        p2("c")

        filterNum(a::contains)  //直接使用之前的函数引用
        filterNum(p2)           //直接使用之前的函数引用


        filterNum(fun (num:String):Boolean{
            return num.contains("8")
        })

        filterNum(fun(num: String): Boolean { //没有函数名,匿名函数
            return num.contains("8")
        })
        filterNum { it.contains("8") }   //Lambda函数
    }

    private fun filterNum(contains: (String) -> Boolean): Boolean {
        return contains("0123456789")
    }


}
