package com.example.kotlin.module

import javax.inject.Inject

class HiltData {
    var a = ""
    var b = 0

    constructor(a: String, b: Int) {
        this.a = a
        this.b = b
    }

    @Inject
    constructor() {
        this.a = "100"
        this.b = 100
    }

    override fun toString(): String {
        return "HiltData(a='$a', b=$b)"
    }
}