package com.example.kotlin.kotlin

class Lambda {
    //匿名函数
    val sum1 = fun(x: Int, y: Int): Int {
        return x + y
    }

    val sum2: (x: Int, y: Int) -> Int = { x, y -> x + y }

    val sum3 = { x: Int, y: Int -> x + y }

    val test = listOf(1, 2, 3).forEach { print(it) }


    fun sum(x: Int, y: Int): Int = x + y


    var name: String? = null
    private fun getRealName() = name ?: ""

    fun lastName(): String? = try {  //try 表达式
        val name = getRealName()
        if (name.isNotEmpty()) name else null  //if表达式
    } catch (_: Throwable) {
        null
    }

}

//枚举
enum class DayofWeek(val day: Int) {
    MON(1),
    TUE(2),
    WEN(3),
    THU(4),
    FRI(5),
    SAT(6),
    SUN(7)
    ; //有额外的方法或属性定义，必须加上分号

    fun getDayNum(): Int {
        return day
    }
}

fun happyDay(day: DayofWeek) = when (day) {
    DayofWeek.SAT -> "666"
    DayofWeek.SUN -> "777"
    else -> ""
}

fun forTest() {
    for (i in 0..100) {  //0-100
        print(i)
    }
    //不包含 100
    for (i in 0 until 100) { //0-99
        print(i)
    }
    //步长为 2
    for (i in 0..100 step 2) { //0,2,4...
        print(i)
    }
    //降序 步长为 2
    for (i in 100 downTo 0 step 2) { //100,98,96...
        print(i)
    }

    print("a" in "abc".."xyz")
    print("a" in listOf("a", "b"))

    val p = Person()
    p called "pp"
}

class Person {
    infix fun called(name: String) {
        print("name is $name")
    }
}