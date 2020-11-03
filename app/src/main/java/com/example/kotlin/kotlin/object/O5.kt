package com.example.kotlin.kotlin.`object`

sealed class Expr  //密闭类
data class Const(val num: Int) : Expr()
data class Sum(val e1: Const, val e2: Const) : Expr()
object NotANumber : Expr()

fun eval(expr: Expr): Int = when (expr) {
    is Const -> expr.num
    is Sum -> eval(expr.e1) + eval(expr.e2) //递归调用
    NotANumber -> Int.MIN_VALUE
}


fun whenTest() {

}

//常量匹配模式
private fun when1(a: Int) = when (a) {
    1 -> "it is 1"
    2 -> "it is 2"
    else -> "it is a numb"
}

//类型模式
sealed class Shape()
class Circle(val radius: Double) : Shape()
class Rectangle(val width: Double, val height: Double) : Shape()
class Triangle(val base: Double, val height: Double) : Shape()

fun getArea(shape: Shape) = when (shape) {
    is Circle -> Math.PI * shape.radius * shape.radius
    is Rectangle -> shape.width * shape.height
    is Triangle -> shape.base * shape.height / 2
}

//逻辑表达式
fun when3(a: Int) = when (a) {
    in 1..9 -> "A"
    100 -> "B"
    else -> "C"
}
