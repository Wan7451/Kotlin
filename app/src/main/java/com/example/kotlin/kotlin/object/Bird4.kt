package com.example.kotlin.kotlin.`object`

import java.util.*

//主构造方法
class Bird4(var age: Int) {

    //从构造方法  花括号可以省略
    constructor(data: Date) : this(getAge(data))
}

class Bird5 private constructor(var age: Int)

open class Bird6(var age: Int) {
    open fun fly() {}
}

class Penguin(age: Int) : Bird6(age) {
    override fun fly() {}
}

fun getAge(data: Date): Int {
    return 10
}

fun test4() {
    val b4 = Bird4(Date())
}

