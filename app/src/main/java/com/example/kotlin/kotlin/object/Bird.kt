package com.example.kotlin.kotlin.`object`

import kotlin.properties.Delegates

class Bird {
    val weight = 500.0f //必须指定属性默认值
    val color = "blue" //自动推导类型
    val food: String? = null
    lateinit var parent: Bird//延迟初始化,必须在使用之前初始化，不然会引起崩溃
    var age = 1
    fun fly() {
    }
}

class Bird2(
    var weight: Float = 500.0f,
    val color: String = "blue",
    var food: String?,
    val age: Int = 1
)

class Bird3(
    weight: Float = 500.0f,
    color: String = "blue",
    food: String?,
    age: Int = 1
) {
    var weight: Float = 500.0f
    var color: String = "blue"
    var food: String? = null
    var age: Int = 1

    init {
        this.weight = weight
        this.color = color
        this.food = food
        this.age = age
    }

    //可以有多个init，从上到下先后执行
    //多init有利于对初始化的操作进行职能分离，简化复杂业务
    init {
        print(this)
    }

    override fun toString(): String {
        return "Bird3(weight=$weight, color='$color', food=$food, age=$age)"
    }

    val sex: String by lazy {
        return@lazy "A"
    }

    var str by Delegates.notNull<String>()
}

fun test() {
    val a = Bird()
    val b = Bird3(food = "apple")
    val c = Bird3(food = "apple", weight = 100f)
}