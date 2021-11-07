package com.example.kotlinpractise

import com.bennyhuo.kotlin.coroutinebasics.utils.log
import kotlin.concurrent.thread
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.resume


//suspend fun main() {
//    log(1)
//    log(returnSuspended())
//    log(2)
//    delay(1000)
//    log(3)
//    log(returnImmediately())
//    log(4)
//}

suspend fun returnSuspended() = suspendCoroutineUninterceptedOrReturn<String> { continuation ->
    thread {
        Thread.sleep(1000)
        continuation.resume("Return suspended.")
    }
    COROUTINE_SUSPENDED
}

class A {
    fun a(int: Int) {
        println("传递进来的是:$int")
    }
    fun b(int: Int) {
        println("传递进来22222的是:$int")
    }

}

class B {
    fun b(): (i: Int) -> Int {
        //TODO:底下这个表示返回的是一个(Int)->Int的函数
        return { params: Int ->
            //TODO:lambda的最后表示函数返回值
            params * params
        }
    }
}

class C {
    //TODO:这种形式相当于A中的方法模板是(Int)->Unit
    //TODO:上述A::a及A::b都满足,因为它们的都是(Int)->Unit
    var foo: A.(int: Int) -> Unit = {
        a(1)
        println("$this ==>")
    }
}

fun main() {
    val a = A()
    //TODO:1.这是传递一个参数,它的函数类型是KFunction1<Int,Unit>,及(Int)->Unit
    //TODO:切记:它并不等于A.(Int)->Unit
    val kk=a::a
    kk(300)

//    var f = A::a //ok
    var f = A::b //ok
//    var foo1 = C().foo //ok
    var foo1 : A.(Int)->Unit  //ok
    foo1 = f
//    foo1 = kk // error
    //TODO:2.这是传递两个参数,相当于A::a这个模板;
    f(a,21)
    foo1.invoke(a, 21)
    C().foo.invoke(a, 31)
    val fb = B().b()
    println(fb(3))
}


suspend fun returnImmediately() = suspendCoroutineUninterceptedOrReturn<String> {
    "Return immediately."
}