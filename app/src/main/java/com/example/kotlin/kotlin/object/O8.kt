package com.example.kotlin.kotlin.`object`

class O8 {
    open class A() {
        fun a() = 0
    }

    class B() : A() {
        fun b() = 0
    }

    fun test() {
        val b = B()
        if (b is A) {
            b.a()
        }
        arrayOf(1)
    }
}

inline fun <reified T> cast(original: Any): T? = original as? T





