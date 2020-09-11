package com.example.kotlin.kotlin

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


}
