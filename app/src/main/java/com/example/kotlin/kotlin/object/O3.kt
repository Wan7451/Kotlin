package com.example.kotlin.kotlin.`object`

interface CanFly {
    fun fly()
}

interface CanEat {
    fun eat()
}

open class Flyerrr : CanFly {
    override fun fly() = print("I can fly")
}

open class Animall : CanEat {
    override fun eat() = print("I can eat")
}

class Bird7(flyerr: Flyerrr, animall: Animall) : CanFly by flyerr, CanEat by animall

fun test7() {
    val b = Bird7(Flyerrr(), Animall())
    b.eat()
    b.fly()

    val d = Bird8(1f, 1, "")
    var (w,a,c)=d
}

data class Bird8(var weight: Float, var age: Int, var color: String)