package com.example.kotlin.kotlin.`object`

open class Horse { //马
    fun runFast() = print("I can run fast")
}

open class Donkey {  //驴
    fun doLongTimeThing() = print("I can do some thing long time")
}

class Mule { //骡子
    fun runFast() = HorseC().runFast()
    fun doLongTimeThing() = DonkeyC().doLongTimeThing()

    //inner 为内部类 内部类引用外部类实例。可访问外部类的属性
    private inner class HorseC : Horse()
    //private 外部类不能访问，有非常好的封装性
    private inner class DonkeyC : Donkey()

    //静态内部类，不持有引用，不能访问属性
    class HorseA : Horse()
}