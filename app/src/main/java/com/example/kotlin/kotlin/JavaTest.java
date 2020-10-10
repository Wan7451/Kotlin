package com.example.kotlin.kotlin;

public class JavaTest {

    public static void main(String[] args) {
        T.a(null); //@JvmStatic  //方法变为静态方法
        T2.INSTANCE.a(null);
    }

}
