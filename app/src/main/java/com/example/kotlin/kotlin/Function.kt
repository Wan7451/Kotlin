package com.example.kotlin.kotlin


/**
 * 扩展函数 都为静态方法
 */

class Function {
    fun MainActivity.a(str: String) {

    }
}
/**
public final class Function {
    public final void a(@NotNull MainActivity $this$a, @NotNull String str) {
        Intrinsics.checkNotNullParameter($this$a, "$this$a");
        Intrinsics.checkNotNullParameter(str, "str");
    }
}
 */


fun MainActivity.aaa(a: Int) {

}
/**
public final class FunctionKt {
    public static final void aaa(@NotNull MainActivity $this$aaa, int a) {
        Intrinsics.checkNotNullParameter($this$aaa, "$this$aaa");
    }
}
*/


object T {
    @JvmStatic  //方法变为静态方法
    fun MainActivity.a() {

    }
}
/**
public final class T {
    public static final T INSTANCE;

    public final void a(@NotNull MainActivity $this$a) {
        Intrinsics.checkNotNullParameter($this$a, "$this$a");
    }

    private T() {
    }

    static {
        T var0 = new T();
        INSTANCE = var0;
    }
}
*/


object T2 {
    fun MainActivity.a() {

    }
}