package com.example.kotlin.kotlin

class KTest {


    val a: String = "a"
    val b = "b" //可以通过类型推导,省略类型声明,提高开发效率
    val c = 10
    val d = 10.0
    val e: String? = null //可空类型


    fun test1(): Unit {
        print("")
    }

    fun test2() {  //Unit 可以省略
        print("")
    }

    fun test3(): Int { //代码块函数体
        return 10
    }

    fun test4(x: Int, y: Int) = x + y //表达式函数体,可以推导出函数的返回值类型
}