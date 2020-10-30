package com.example.kotlin.kotlin.`object`

class O1 {
}

interface Flyerr {
    fun fly()
    fun kind() = "flying animals"
}

interface Animal {
    val name: String
    fun eat()
    fun kind() = "flying animals"
}

class Birdd(override val name: String) : Flyerr, Animal {
    override fun fly() {
        print("I can fly")
    }

    override fun eat() {
        print("I can eat")
    }

    override fun kind(): String {
        return super<Flyerr>.kind()
    }

}