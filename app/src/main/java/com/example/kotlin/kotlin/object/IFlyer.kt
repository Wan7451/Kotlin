package com.example.kotlin.kotlin.`object`

interface IFlyer {
    //抽象属性
    val speed: Int

    //抽象属性默认实现
    val height: Int
        get() {
            return 500
        }

    //抽象方法
    fun kind()

    //抽象方法默认实现
    fun fly() {
        print("I can fly")
    }
}

/**
 * 默认实现可以不用重写
 */
class Flyer(override val speed: Int) : IFlyer {
    override fun kind() {
    }

    override val height: Int
        get() = 10
}